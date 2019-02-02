package com.rudenko.alexandr.vjettest.data.sources

import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import io.reactivex.Completable
import io.reactivex.Single

interface AppRepository {

    fun getSources(): Single<List<Source>>

    fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>>

    fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>>

    fun getFavorites(): Single<List<Article>>

    fun saveArticleToFavorites(article: Article): Completable

    fun removeArticleFromFavorites(article: Article): Completable

}