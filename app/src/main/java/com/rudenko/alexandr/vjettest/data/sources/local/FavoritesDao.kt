package com.rudenko.alexandr.vjettest.data.sources.local

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
abstract class FavoritesDao {

    @Query("SELECT * FROM favorite_articles ORDER BY updated DESC")
    abstract fun getAll(): Single<List<FavoriteArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertArticle(article: FavoriteArticle)

    @Delete
    abstract fun deleteArticle(article: FavoriteArticle)

}