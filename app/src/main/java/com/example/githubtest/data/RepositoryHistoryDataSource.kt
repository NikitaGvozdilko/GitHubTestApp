package com.example.githubtest.data

import com.example.githubtest.data.database.dao.RepoHistoryDao
import com.example.githubtest.data.database.entity.OpenedRepoEntity
import com.example.githubtest.data.mapping.toDomain
import com.example.githubtest.domain.model.OpenedRepository
import com.example.githubtest.domain.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RepositoryHistoryDataSource {
    fun getOpenedRepositoryIds(): Flow<List<Long>>
    fun getOpenedRepositories(): Flow<List<OpenedRepository>>
    suspend fun insertRepository(repository: Repository)
}

class RepositoryHistoryDataSourceImpl @Inject constructor(
    private val dao: RepoHistoryDao
) : RepositoryHistoryDataSource {
    override fun getOpenedRepositoryIds(): Flow<List<Long>> {
        return dao.getOpenedRepositories().map { list ->
            list.map { it.id }
        }
    }

    override fun getOpenedRepositories(): Flow<List<OpenedRepository>> {
        return dao.getOpenedRepositoriesSorted().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertRepository(repository: Repository) {
        val time = System.currentTimeMillis()
        dao.insert(OpenedRepoEntity(repository.id, repository.title, time))
    }

}