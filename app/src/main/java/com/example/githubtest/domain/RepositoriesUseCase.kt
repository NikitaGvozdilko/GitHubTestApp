package com.example.githubtest.domain

import android.util.Log
import com.example.githubtest.data.RepositoryHistoryDataSource
import com.example.githubtest.data.RepositoryPagingDataSource
import com.example.githubtest.domain.model.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface RepositoriesUseCase {
    val repositoriesFlow: Flow<List<Repository>>
    suspend fun searchRepositories(query: String)
    suspend fun loadNextPage()
    suspend fun repositoryOpened(repository: Repository)
    suspend fun clearSearchResults()
}

class RepositoriesUseCaseImpl @Inject constructor(
    private val pagingDataSource: RepositoryPagingDataSource,
    private val historyDataSource: RepositoryHistoryDataSource
) : RepositoriesUseCase {
    override val repositoriesFlow: Flow<List<Repository>> = combine(
        pagingDataSource.repositoriesFlow, historyDataSource.getOpenedRepositoryIds()
    ) { repositories, openedIds ->
        repositories.map { repository ->
            if (openedIds.contains(repository.id)) {
                repository.copy(isOpened = true)
            } else {
                repository
            }
        }
    }

    private var useDelay: Boolean = false

    override suspend fun searchRepositories(query: String) {
        pagingDataSource.searchRepositories(query)
    }

    override suspend fun loadNextPage() {
        if (useDelay) {
            Log.e("Tag","Delay")
            delay(2000)
        }
        try {
            useDelay = false
            pagingDataSource.loadNextPage()
        } catch (ex: Exception) {
            useDelay = true
            throw ex
        }
    }

    override suspend fun repositoryOpened(repository: Repository) {
        historyDataSource.insertRepository(repository)
    }

    override suspend fun clearSearchResults() {
        pagingDataSource.clearData()
    }
}