package com.example.githubtest.data

import com.example.githubtest.data.api.GitHubApi
import com.example.githubtest.data.mapping.toDomain
import com.example.githubtest.domain.model.Repository
import javax.inject.Inject

interface GitHubDataSource {
    suspend fun getRepositories(query: String, page: Int = 0, itemsPerPage: Int = 15): List<Repository>
}

class GitHubDataSourceImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : GitHubDataSource {
    override suspend fun getRepositories(query: String, page: Int, itemsPerPage: Int): List<Repository> {
        return gitHubApi.getRepositories(query = query, page = page, itemsPerPage = itemsPerPage).items?.map {
            it.toDomain()
        } ?: emptyList()
    }
}