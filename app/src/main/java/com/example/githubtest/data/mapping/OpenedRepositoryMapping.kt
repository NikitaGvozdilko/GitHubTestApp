package com.example.githubtest.data.mapping

import com.example.githubtest.data.database.entity.OpenedRepoEntity
import com.example.githubtest.domain.model.OpenedRepository


fun OpenedRepoEntity.toDomain(): OpenedRepository {
    return OpenedRepository(
        id = id,
        title = title,
        openedAt = openedAt
    )
}