package com.example.githubtest.ui.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.githubtest.databinding.FragmentHistoryBinding
import com.example.githubtest.ui.common.BaseFragment
import com.example.githubtest.ui.common.recycler.BuildDelegationAdapter
import com.example.githubtest.ui.common.recycler.ItemModel
import com.example.githubtest.ui.repositories.adapter.RepositoriesDiffUtil
import com.example.githubtest.ui.repositories.adapter.repositoriesAdapter
import com.example.githubtest.utils.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val viewModel: HistoryViewModel by viewModels()
    private val adapter by lazy {
        BuildDelegationAdapter<ItemModel> {
            add(repositoriesAdapter(), RepositoriesDiffUtil())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRepositories.adapter = adapter

        launchWhenStarted(viewModel.stateFlow) { state ->
            binding.tvEmpty.isVisible = state.list.isEmpty()
            adapter.submitList(state.list)
        }
    }
}