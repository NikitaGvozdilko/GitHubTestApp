package com.example.githubtest.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.githubtest.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.*
import javax.inject.Inject

class GitHubAuthHandler @Inject constructor(@ApplicationContext context: Context) {

    private val service: AuthorizationService = AuthorizationService(context)

    fun createIntent(): Intent {
        val redirectUri = Uri.parse("hedgehog://callback")
        val authorizeUri = Uri.parse("https://github.com/login/oauth/authorize")
        val tokenUri = Uri.parse("https://github.com/login/oauth/access_token")

        val config = AuthorizationServiceConfiguration(authorizeUri, tokenUri)
        val request = AuthorizationRequest
            .Builder(config, Constants.CLIENT_ID, ResponseTypeValues.CODE, redirectUri)
            .setScopes("user repo admin")
            .build()
        return service.getAuthorizationRequestIntent(request)
    }

    fun getToken(
        tokenRequest: TokenRequest,
        onError: (AuthorizationException) -> Unit,
        onResult: (String) -> Unit
    ) {
        val secret = ClientSecretBasic(Constants.SECRET_ID)
        service.performTokenRequest(tokenRequest, secret) { res, exception ->
            if (exception != null) {
                onError(exception)
            } else {
                res?.accessToken?.also { token ->
                    onResult(token)
                }
            }
        }
    }
}