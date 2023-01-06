package com.example.githubtest.ui.mapping

import com.example.githubtest.domain.model.Repository
import com.example.githubtest.ui.repositories.adapter.RepositoryItemModel

fun Repository.toItemModel(): RepositoryItemModel {
    return RepositoryItemModel(
        id = id,
        title = title,
        isOpened = isOpened
    )
}