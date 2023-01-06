package com.example.githubtest.domain

import com.example.githubtest.data.RepositoryHistoryDataSource
import com.example.githubtest.domain.model.OpenedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HistoryUseCase {
    suspend fun getHistoryRepositories(): Flow<List<OpenedRepository>>
}

class HistoryUseCaseImpl @Inject constructor(
    private val historyDataSource: RepositoryHistoryDataSource
) : HistoryUseCase {
    override suspend fun getHistoryRepositories(): Flow<List<OpenedRepository>> {
        return historyDataSource.getOpenedRepositories()
    }

}