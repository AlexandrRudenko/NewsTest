package com.rudenko.alexandr.vjettest.di

import com.rudenko.alexandr.vjettest.data.sources.AppRepository
import com.rudenko.alexandr.vjettest.ui.articles.ArticlesContract
import com.rudenko.alexandr.vjettest.ui.articles.ArticlesPresenter
import dagger.Module
import dagger.Provides

@Module
class ViewModule {

    @Provides
    fun provideArticlesPresenter(repository: AppRepository): ArticlesContract.Presenter = ArticlesPresenter(repository)


}