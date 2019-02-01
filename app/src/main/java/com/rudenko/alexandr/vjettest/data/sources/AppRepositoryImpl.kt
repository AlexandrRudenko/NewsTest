package com.rudenko.alexandr.vjettest.data.sources

import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource

) : AppRepository {

    override fun getSources(): Single<List<Source>> =
        remoteDataSource.getSources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>> =
        remoteDataSource.getTopHeadlines(page, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>> =
        remoteDataSource.getArticles(searchParameters, page, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}