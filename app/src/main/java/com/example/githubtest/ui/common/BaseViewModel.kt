package com.example.githubtest.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtest.utils.ExceptionParser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    private val _errorChannel = Channel<String>(Channel.BUFFERED)
    val errorFlow: Flow<String> = _errorChannel.receiveAsFlow()

    @Inject
    lateinit var exceptionParser: ExceptionParser

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        sendError(exceptionParser.parseError(throwable))
    }

    fun sendError(error: String) {
        _errorChannel.trySend(error)
    }

    fun handleError(throwable: Throwable) {
        sendError(exceptionParser.parseError(throwable))
    }

    protected fun launch(
        onFinally: suspend CoroutineScope.() -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(coroutineExceptionHandler) {
            try {
                block()
            } finally {
                onFinally()
            }
        }
    }
}