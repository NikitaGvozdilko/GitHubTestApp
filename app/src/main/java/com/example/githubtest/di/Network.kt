package com.example.githubtest.di

import com.example.githubtest.data.api.GitHubApi
import com.example.githubtest.data.network.AuthInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Network {

    @[Binds QAuthInterceptor]
    abstract fun bindAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor

    companion object {

        @Singleton
        @Provides
        fun provideRetrofit(@QAuthInterceptor authInterceptor: Interceptor): Retrofit {
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
            return retrofit.create(GitHubApi::class.java)
        }
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class QAuthInterceptor
}