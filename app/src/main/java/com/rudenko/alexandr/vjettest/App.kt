package com.rudenko.alexandr.vjettest

import android.app.Application
import com.rudenko.alexandr.vjettest.di.AppComponent
import com.rudenko.alexandr.vjettest.di.DaggerAppComponent

open class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }

}