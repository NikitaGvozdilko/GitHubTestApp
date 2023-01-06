package com.example.githubtest.data.model

import com.google.gson.annotations.SerializedName

class RepositoriesResponse {
    @SerializedName("total_count")
    var totalCount : Int = 0

    @SerializedName("incomplete_results")
    var isinCompleteResults : Boolean = false

    @SerializedName("items")
    var items : List<RepositoryDto>? = null
}