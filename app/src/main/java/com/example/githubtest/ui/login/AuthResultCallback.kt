package com.example.githubtest.ui.login

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenRequest

class AuthResultCallback(
    private val onSuccess: (TokenRequest) -> Unit,
    private val onError: (String?) -> Unit,
) : ActivityResultCallback<ActivityResult> {
    override fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val ex = AuthorizationException.fromIntent(result.data!!)
            val response = AuthorizationResponse.fromIntent(result.data!!)
            if (ex != null) {
                Log.e("Github Auth", "launcher: $ex")
                onError(ex.error)
            } else {
                val tokenRequest = response?.createTokenExchangeRequest()
                onSuccess(tokenRequest!!)
            }
        } else {
            onError(null)
        }
    }
}