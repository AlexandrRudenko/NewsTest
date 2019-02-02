package com.rudenko.alexandr.vjettest.data.sources.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [FavoriteArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

}