package com.example.githubtest.di

import com.example.githubtest.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindAuthUseCase(authUseCaseImpl: AuthUseCaseImpl): AuthUseCase


    @Binds
    abstract fun bindRepositoriesUseCase(repositoriesUseCase: RepositoriesUseCaseImpl): RepositoriesUseCase

    @Binds
    abstract fun bindHistoryUseCase(historyUseCase: HistoryUseCaseImpl): HistoryUseCase
}