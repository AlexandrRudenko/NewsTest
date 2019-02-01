package com.rudenko.alexandr.vjettest.data.sources.remote

import com.google.gson.annotations.SerializedName
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source

data class Response(
    @SerializedName("status")
    val status: String,

    @SerializedName("articles")
    val articles: List<Article>?,

    @SerializedName("sources")
    val sources: List<Source>?,

    @SerializedName("totalResults")
    val totalResults: Int
)