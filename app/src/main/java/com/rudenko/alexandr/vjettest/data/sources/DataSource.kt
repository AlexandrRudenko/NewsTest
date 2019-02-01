package com.rudenko.alexandr.vjettest.data.sources

import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import io.reactivex.Single

interface DataSource {

    fun getSources(): Single<List<Source>>

    fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>>

    fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>>

}