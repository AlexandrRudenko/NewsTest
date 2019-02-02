package com.rudenko.alexandr.vjettest.di

import com.rudenko.alexandr.vjettest.App
import com.rudenko.alexandr.vjettest.Const
import com.rudenko.alexandr.vjettest.data.sources.DataSource
import com.rudenko.alexandr.vjettest.data.sources.local.LocalDataSource
import com.rudenko.alexandr.vjettest.data.sources.remote.ApiInterface
import com.rudenko.alexandr.vjettest.data.sources.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", Const.API_KEY)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    @Named("REMOTE")
    fun provideRemoteDataSource(apiInterface: ApiInterface): DataSource = RemoteDataSource(apiInterface)

    @Provides
    @Singleton
    @Named("LOCAL")
    fun provideLocalDataSource(): DataSource = LocalDataSource(App.instance.database.favoritesDao())

}