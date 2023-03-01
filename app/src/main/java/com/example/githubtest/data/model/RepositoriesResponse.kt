package com.example.githubtest.data.model

import com.google.gson.annotations.SerializedName

class RepositoriesResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,

    @SerializedName("incomplete_results")
    val isinCompleteResults: Boolean = false,

    @SerializedName("items")
    val items: List<RepositoryDto>? = null
)