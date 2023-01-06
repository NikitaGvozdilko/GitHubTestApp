package com.example.githubtest.data

import com.example.githubtest.domain.model.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface RepositoryPagingDataSource {
    val repositoriesFlow: Flow<List<Repository>>
    suspend fun searchRepositories(query: String)
    suspend fun loadNextPage()
    suspend fun clearData()
}

class RepositoryPagingDataSourceImpl @Inject constructor(
    private val gitHubDataSource: GitHubDataSource
) : RepositoryPagingDataSource {

    private var page: Int = 1
    private var list: List<Repository> = emptyList()
    private var query: String = ""
    private var isPagingFinished = false

    private val _repositoriesFlow = MutableStateFlow<List<Repository>>(emptyList())
    override val repositoriesFlow: Flow<List<Repository>> = _repositoriesFlow.asStateFlow()

    override suspend fun searchRepositories(query: String) {
        this.query = query
        page = 1
        list = emptyList()
        coroutineScope {
            val firstPage = async {
                loadData(page)
            }

            val secondPage = async {
                loadData(page + 1)
            }

            updateData(firstPage.await())
            updateData(secondPage.await())
            page++
        }
    }

    override suspend fun loadNextPage() {
        if (!isPagingFinished) {
            coroutineScope {
                val firstResult = async {
                    loadData(page + 1)
                }

                val secondResult = async {
                    loadData(page + 2)
                }
                updateData(firstResult.await())
                page++
                updateData(secondResult.await())
                page++
            }
        }
    }

    override suspend fun clearData() {
        list = emptyList()
        page = 1
        query = ""
        isPagingFinished = false
        _repositoriesFlow.value = list
    }

    private suspend fun loadData(page: Int): List<Repository> {
        return gitHubDataSource.getRepositories(query, page, ITEMS_PER_PAGE)
    }

    private suspend fun updateData(repositories: List<Repository>) {
        val newList = ArrayList(list) + repositories
        list = newList
        if (repositories.size < ITEMS_PER_PAGE) {
            isPagingFinished = true
        }
        _repositoriesFlow.emit(newList)
    }

    private companion object {
        const val ITEMS_PER_PAGE = 15
    }
}