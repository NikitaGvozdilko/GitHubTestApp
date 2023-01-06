package com.example.githubtest.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubtest.data.*
import com.example.githubtest.data.database.GitHubDataBase
import com.example.githubtest.data.database.dao.RepoHistoryDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource

    @Singleton
    @Binds
    abstract fun bindGitHubDataSource(gitHubDataSource: GitHubDataSourceImpl): GitHubDataSource

    @Singleton
    @Binds
    abstract fun bindRepositoryPagingDataSource(repositoryPagingDataSource: RepositoryPagingDataSourceImpl): RepositoryPagingDataSource

    @Singleton
    @Binds
    abstract fun bindRepositoryHistoryDataSource(repositoryHistoryDataSource: RepositoryHistoryDataSourceImpl): RepositoryHistoryDataSource

    companion object {
        @Singleton
        @Provides
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return context.authDataStore
        }

        @Singleton
        @Provides
        fun provideDataBase(@ApplicationContext context: Context): GitHubDataBase {
            return Room.databaseBuilder(context, GitHubDataBase::class.java, GitHubDataBase.NAME)
                .build()
        }

        @Singleton
        @Provides
        fun provideRepoHistoryDao(dataBase: GitHubDataBase): RepoHistoryDao {
            return dataBase.repoHistoryDao
        }

        private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore("Auth")

    }
}