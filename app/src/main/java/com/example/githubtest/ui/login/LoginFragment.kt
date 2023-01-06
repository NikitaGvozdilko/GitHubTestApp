package com.example.githubtest.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.githubtest.R
import com.example.githubtest.databinding.FragmentLoginBinding
import com.example.githubtest.ui.common.BaseFragment
import com.example.githubtest.utils.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var authHandler: GitHubAuthHandler

    private val authCallback: AuthResultCallback by lazy {
        AuthResultCallback(
            onSuccess = { tokenRequest ->
                authHandler.getToken(tokenRequest = tokenRequest,
                    onResult = { token ->
                        viewModel.setToken(token)
                    }, onError = { exception ->
                        viewModel.onError(
                            exception.error ?: requireContext().getString(R.string.auth_failed)
                        )
                    })
            },
            onError = { error ->
                viewModel.onError(error ?: requireContext().getString(R.string.auth_failed))
            }
        )
    }


    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), authCallback
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchWhenStarted(viewModel.stateFlow) {
            binding.pbLoading.isVisible = it.isLoading
            binding.btnLogin.isEnabled = !it.isLoading
        }

        binding.btnLogin.setOnClickListener {
            viewModel.setLoadingState(true)
            githubAuth()
        }
    }

    private fun githubAuth() {
        val intent = authHandler.createIntent()
        launcher.launch(intent)
    }
}