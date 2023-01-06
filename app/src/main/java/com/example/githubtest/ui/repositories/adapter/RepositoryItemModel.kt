package com.example.githubtest.ui.repositories.adapter

import com.example.githubtest.ui.common.recycler.ItemModel

data class RepositoryItemModel(
    val id: Long,
    val title: String,
    val isOpened: Boolean
) : ItemModel