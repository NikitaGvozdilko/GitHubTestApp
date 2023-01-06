package com.example.githubtest.di

import com.example.githubtest.ui.navigation.Router
import com.example.githubtest.ui.navigation.RouterImpl
import com.example.githubtest.utils.ExceptionParser
import com.example.githubtest.utils.ExceptionParserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Singleton
    @Binds
    abstract fun bindRouter(routerImpl: RouterImpl): Router

    @Singleton
    @Binds
    abstract fun bindExceptionParser(exceptionParser: ExceptionParserImpl): ExceptionParser
}