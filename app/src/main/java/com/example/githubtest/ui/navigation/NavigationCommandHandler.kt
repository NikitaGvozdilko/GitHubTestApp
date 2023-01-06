package com.example.githubtest.ui.navigation

import androidx.navigation.NavController
import com.example.githubtest.ui.login.LoginFragmentDirections
import com.example.githubtest.ui.repositories.RepositoriesFragmentDirections

object NavigationCommandHandler {
    fun handle(navController: NavController, navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.Navigate -> {
                when (navigationCommand.screen) {
                    Screen.Repositories -> {
                        navController.navigate(LoginFragmentDirections.actionLoginFragmentToRepositoriesFragment())
                    }
                    Screen.History -> {
                        navController.navigate(RepositoriesFragmentDirections.actionRepositoriesFragmentToHistoryFragment())
                    }
                }
            }
            NavigationCommand.PopBackstack -> {

            }
        }
    }
}