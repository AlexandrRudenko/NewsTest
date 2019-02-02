package com.rudenko.alexandr.vjettest

import android.app.Application
import android.arch.persistence.room.Room
import com.rudenko.alexandr.vjettest.data.sources.local.AppDatabase
import com.rudenko.alexandr.vjettest.di.AppComponent
import com.rudenko.alexandr.vjettest.di.DaggerAppComponent

open class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var component: AppComponent
        private set

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
        component = buildComponent()

    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }

}