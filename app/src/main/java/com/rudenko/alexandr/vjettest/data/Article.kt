package com.rudenko.alexandr.vjettest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    @SerializedName("source")
    var source: Source,

    @SerializedName("author")
    var author: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("urlToImage")
    var urlToImage: String?,

    @SerializedName("publishedAt")
    var publishedAt: String
) : Parcelable