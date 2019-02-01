package com.rudenko.alexandr.vjettest.di

import com.rudenko.alexandr.vjettest.ui.articles.ArticlesFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ModelModule::class, ViewModule::class, PresenterModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: ArticlesFragment)

}