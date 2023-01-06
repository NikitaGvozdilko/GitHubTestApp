package com.example.githubtest.utils

import android.util.Log

fun <T> getValueOrThrow(value: T?, exceptionMessage: String) =
    value ?: run {
        Log.e("Parsing error", exceptionMessage)
        throw Exception(exceptionMessage)
    }