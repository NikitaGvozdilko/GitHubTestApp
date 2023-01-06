package com.example.githubtest.ui.repositories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.githubtest.databinding.ItemRepositoryBinding
import com.example.githubtest.ui.common.recycler.adapterDelegate

fun repositoriesAdapter(onItemClicked: (RepositoryItemModel) -> Unit = {}) = adapterDelegate<RepositoryItemModel, ItemRepositoryBinding> {
    onBind {
        with (binding) {
            tvTitle.text = item.title
            if (item.isOpened) {
                root.setCardBackgroundColor(context.getColor(android.R.color.darker_gray))
            } else {
                root.setCardBackgroundColor(context.getColor(android.R.color.white))
            }
            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}

class RepositoriesDiffUtil : DiffUtil.ItemCallback<RepositoryItemModel>() {
    override fun areItemsTheSame(
        oldItem: RepositoryItemModel,
        newItem: RepositoryItemModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RepositoryItemModel,
        newItem: RepositoryItemModel
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.isOpened == newItem.isOpened
    }

}