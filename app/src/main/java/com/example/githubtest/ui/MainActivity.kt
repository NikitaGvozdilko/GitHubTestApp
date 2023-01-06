package com.example.githubtest.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.githubtest.R
import com.example.githubtest.ui.navigation.NavigationCommandHandler
import com.example.githubtest.utils.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val navigationController: NavController
        get() = findNavController(R.id.nav_host_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchWhenStarted(viewModel.isAuthorizedFlow) { isAuthorized ->
            val graphInflater = navigationController.navInflater
            val navGraph = graphInflater.inflate(R.navigation.navigation_main)
            if (isAuthorized) {
                navGraph.setStartDestination(R.id.repositoriesFragment)
            } else {
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navigationController.setGraph(navGraph, bundleOf())
        }

        launchWhenStarted(viewModel.navigationCommandFlow) {
            NavigationCommandHandler.handle(navigationController, it)
        }
    }
}