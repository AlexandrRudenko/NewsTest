package com.rudenko.alexandr.vjettest.di

import com.rudenko.alexandr.vjettest.data.sources.AppRepository
import com.rudenko.alexandr.vjettest.data.sources.AppRepositoryImpl
import com.rudenko.alexandr.vjettest.data.sources.DataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideAppRepository(
        @Named("REMOTE") remoteDataSource: DataSource,
        @Named("LOCAL") localDataSource: DataSource
    ): AppRepository = AppRepositoryImpl(remoteDataSource, localDataSource)

}