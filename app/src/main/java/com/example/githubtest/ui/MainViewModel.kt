package com.example.githubtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtest.domain.AuthUseCase
import com.example.githubtest.ui.navigation.NavigationCommand
import com.example.githubtest.ui.navigation.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val router: Router,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _isAuthorizedFlow = Channel<Boolean>(Channel.BUFFERED)
    val isAuthorizedFlow: Flow<Boolean> = _isAuthorizedFlow.receiveAsFlow()

    val navigationCommandFlow: Flow<NavigationCommand> = router.navigationCommandFlow

    init {
        viewModelScope.launch {
            authUseCase.isAuthorized().first().also {
                _isAuthorizedFlow.send(it)
            }
        }
    }

}