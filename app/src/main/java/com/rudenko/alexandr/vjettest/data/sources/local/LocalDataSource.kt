package com.rudenko.alexandr.vjettest.data.sources.local

import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import com.rudenko.alexandr.vjettest.data.sources.DataSource
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class LocalDataSource : DataSource {
    override fun getSources(): Single<List<Source>> {
        throw UnsupportedOperationException()
    }

    override fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>> {
        throw UnsupportedOperationException()
    }

    override fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>> {
        throw UnsupportedOperationException()
    }
}