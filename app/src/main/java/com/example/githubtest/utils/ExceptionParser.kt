package com.example.githubtest.utils

import android.content.Context
import com.example.githubtest.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.inject.Inject

interface ExceptionParser {
    fun parseError(throwable: Throwable): String
}

class ExceptionParserImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ExceptionParser {
    override fun parseError(throwable: Throwable): String {
        throwable.printStackTrace()
        return when (throwable) {
            is IOException -> {
                when (throwable) {
                    is ConnectException,
                    is SocketTimeoutException,
                    is SocketException -> {
                        return context.getString(R.string.error_unable_communicate_server)
                    }
                    !is InterruptedIOException -> {
                        return context.getString(R.string.error_no_internet_connection)
                    }
                    else -> context.getString(R.string.error_something_went_wrong)
                }
            }
            else -> context.getString(R.string.error_something_went_wrong)
        }
    }
}