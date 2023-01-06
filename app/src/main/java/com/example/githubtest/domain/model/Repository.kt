package com.example.githubtest.domain.model

data class Repository(
    val id: Long,
    val title: String,
    val url: String?,
    val isOpened: Boolean = false
)