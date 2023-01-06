package com.example.githubtest.data.network

import com.example.githubtest.data.AuthDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

private const val HEADER_AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer"

class AuthInterceptor @Inject constructor(
    private val authDataSource: AuthDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return updateRequest(chain)
    }

    private fun updateRequest(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()

        val token = getToken()
        request.addHeader(HEADER_AUTHORIZATION, "$BEARER $token")

        val newRequest = request.method(originalRequest.method, originalRequest.body).build()
        val response = chain.proceed(newRequest)
        return response
    }

    private fun getToken(): String {
        val tokenData = runBlocking {
            authDataSource.getToken().first().orEmpty()
        }
        return tokenData
    }
}