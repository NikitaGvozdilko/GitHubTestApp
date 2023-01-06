package com.example.githubtest.data.mapping

import com.example.githubtest.data.model.RepositoriesResponse
import com.example.githubtest.data.model.RepositoryDto
import com.example.githubtest.domain.model.Repository
import com.example.githubtest.utils.getValueOrThrow

fun RepositoryDto.toDomain(): Repository {
    return Repository(
        id = getValueOrThrow(id, "Id is null"),
        title = fullName ?: "",
        url = htmlUrl
    )
}