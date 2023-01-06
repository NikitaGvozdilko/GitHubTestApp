package com.example.githubtest.ui.repositories

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.githubtest.databinding.FragmentRepositoriesBinding
import com.example.githubtest.ui.common.BaseFragment
import com.example.githubtest.ui.common.recycler.BuildDelegationAdapter
import com.example.githubtest.ui.common.recycler.ItemModel
import com.example.githubtest.ui.repositories.adapter.ProgressDiffUtil
import com.example.githubtest.ui.repositories.adapter.RepositoriesDiffUtil
import com.example.githubtest.ui.repositories.adapter.progressAdapter
import com.example.githubtest.ui.repositories.adapter.repositoriesAdapter
import com.example.githubtest.utils.extensions.launchWhenStarted
import com.example.githubtest.utils.extensions.scrollEndFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepositoriesFragment : BaseFragment<FragmentRepositoriesBinding>() {
    override val viewModel: RepositoriesViewModel by viewModels()

    private val adapter by lazy {
        BuildDelegationAdapter<ItemModel> {
            add(repositoriesAdapter(viewModel::onRepoClicked), RepositoriesDiffUtil())
            add(progressAdapter(), ProgressDiffUtil())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        launchWhenStarted(viewModel.stateFlow) { state ->
            binding.tvEmpty.isVisible = state.data.isEmpty()
            binding.pbLaoding.isVisible = state.isLoading
            adapter.submitList(state.data) {
                if (state.scrollToStart) {
                    binding.rvRepositories.smoothScrollToPosition(0)
                }
            }
        }

        launchWhenStarted(viewModel.actionFlow) { action ->
            when (action) {
                is RepositoriesViewModel.Action.OpenRepository -> {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(requireContext(), Uri.parse(action.url))
                }
            }
        }
    }

    private fun initView() {
        binding.rvRepositories.adapter = adapter
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            text?.also {
                viewModel.onTextChanged(it.toString())
            }
        }

        binding.rvRepositories.scrollEndFlow {
            viewModel.loadNextPage()
        }

        binding.fbHistory.setOnClickListener {
            viewModel.onHistoryClicked()
        }
    }
}