package com.example.githubtest.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubtest.data.database.GitHubDataBase
import com.example.githubtest.domain.model.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RepositoryHistoryDataSourceImplTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val database: GitHubDataBase =
        Room.inMemoryDatabaseBuilder(testContext, GitHubDataBase::class.java).build()
    private val repositoryHistoryDataSource =
        RepositoryHistoryDataSourceImpl(database.repoHistoryDao)

    @Test
    fun testSavingRepositories() = runTest {
        val repository = Repository(0, "google/gson", "")
        repositoryHistoryDataSource.insertRepository(repository)

        assertTrue(
            repositoryHistoryDataSource.getOpenedRepositories().first().first().id == repository.id
        )
    }
}