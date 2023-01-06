package com.example.githubtest.ui.mapping

import com.example.githubtest.domain.model.OpenedRepository
import com.example.githubtest.ui.repositories.adapter.RepositoryItemModel

fun OpenedRepository.toItemModel(): RepositoryItemModel {
    return RepositoryItemModel(
        id = id,
        title = title,
        isOpened = false
    )
}