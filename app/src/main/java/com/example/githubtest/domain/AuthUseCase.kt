package com.example.githubtest.domain

import com.example.githubtest.data.AuthDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface AuthUseCase {
    suspend fun saveToken(token: String)
    suspend fun isAuthorized(): Flow<Boolean>
}

class AuthUseCaseImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthUseCase {
    override suspend fun saveToken(token: String) {
        authDataSource.saveToken(token)
    }

    override suspend fun isAuthorized(): Flow<Boolean> {
        return authDataSource.getToken().map {
            it != null
        }
    }
}