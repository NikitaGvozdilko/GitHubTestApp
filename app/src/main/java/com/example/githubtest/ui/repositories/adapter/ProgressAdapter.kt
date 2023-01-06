package com.example.githubtest.ui.repositories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.githubtest.databinding.ItemLoadingBinding
import com.example.githubtest.ui.common.recycler.adapterDelegate

fun progressAdapter() = adapterDelegate<ProgressItemModel, ItemLoadingBinding> {}

class ProgressDiffUtil: DiffUtil.ItemCallback<ProgressItemModel>() {
    override fun areItemsTheSame(oldItem: ProgressItemModel, newItem: ProgressItemModel): Boolean {
        return true
    }

    override fun areContentsTheSame(
        oldItem: ProgressItemModel,
        newItem: ProgressItemModel
    ): Boolean {
        return true
    }

}