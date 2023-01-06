package com.example.githubtest.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface Router {
    val navigationCommandFlow: SharedFlow<NavigationCommand>
    suspend fun navigate(navigationCommand: NavigationCommand)
}

class RouterImpl @Inject constructor(): Router {
    private val _navigationCommandFlow = MutableSharedFlow<NavigationCommand>()
    override val navigationCommandFlow: SharedFlow<NavigationCommand> = _navigationCommandFlow.asSharedFlow()

    override suspend fun navigate(navigationCommand: NavigationCommand) {
        _navigationCommandFlow.emit(navigationCommand)
    }

}