package com.example.githubtest.ui.navigation

sealed class Screen {
    object Repositories: Screen()
    object History: Screen()
}
