package com.example.githubtest.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubtest.data.api.GitHubApi
import com.example.githubtest.data.model.RepositoriesResponse
import com.example.githubtest.data.model.RepositoryDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RepositoryPagingDataSourceImplTest {
    private lateinit var repository: RepositoryPagingDataSourceImpl

    @Before
    fun init() {
        val gitHubDataSource = GitHubDataSourceImpl(GitHubApiTest())
        repository = RepositoryPagingDataSourceImpl(gitHubDataSource)
    }

    @Test
    fun testLoading() = runTest {
        repository.searchRepositories("")
        repository.loadNextPage()
        assertTrue(repository.repositoriesFlow.first().size == ITEMS_PER_PAGE * 4)
    }

    companion object {
        private const val ITEMS_PER_PAGE = 15
    }
}

private class GitHubApiTest : GitHubApi {
    private val repositories = List(100) { index ->
        RepositoryDto(id = index.toLong(), fullName = "Repo $index")
    }

    override suspend fun getRepositories(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): RepositoriesResponse {
        return RepositoriesResponse(
            items = repositories.subList(
                page * itemsPerPage,
                page * itemsPerPage + itemsPerPage
            )
        )
    }
}