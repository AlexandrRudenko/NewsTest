package com.rudenko.alexandr.vjettest.data.sources.local

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.rudenko.alexandr.vjettest.data.Source

@Entity(tableName = "favorite_articles")
data class FavoriteArticle(
    @Embedded
    var source: Source,
    var author: String?,
    var title: String,
    var description: String?,
    @PrimaryKey
    var url: String,
    var urlToImage: String?,
    var publishedAt: String,
    var updated: Long
)