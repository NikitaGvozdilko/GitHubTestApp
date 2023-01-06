package com.example.githubtest.ui.navigation

sealed class NavigationCommand {
    class Navigate(val screen: Screen) : NavigationCommand()
    object PopBackstack: NavigationCommand()
}
