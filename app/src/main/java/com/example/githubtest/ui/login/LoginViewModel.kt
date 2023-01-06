package com.example.githubtest.ui.login

import com.example.githubtest.domain.AuthUseCase
import com.example.githubtest.ui.common.BaseViewModel
import com.example.githubtest.ui.navigation.NavigationCommand
import com.example.githubtest.ui.navigation.Router
import com.example.githubtest.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val router: Router,
    private val authUseCase: AuthUseCase
) : BaseViewModel() {
    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    fun setToken(token: String) {
        launch {
            authUseCase.saveToken(token)
            router.navigate(NavigationCommand.Navigate(Screen.Repositories))
        }
    }

    fun setLoadingState(isLoading: Boolean) {
        _stateFlow.update { state ->
            state.copy(isLoading = isLoading)
        }
    }

    fun onError(error: String) {
        sendError(error)
        setLoadingState(false)
    }

    data class State(val isLoading: Boolean = false)
}