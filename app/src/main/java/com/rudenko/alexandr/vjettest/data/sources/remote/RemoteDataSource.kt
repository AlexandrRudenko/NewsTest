package com.rudenko.alexandr.vjettest.data.sources.remote

import android.annotation.SuppressLint
import com.rudenko.alexandr.vjettest.Const
import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import com.rudenko.alexandr.vjettest.data.sources.DataSource
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException
import java.text.SimpleDateFormat
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface
) : DataSource {

    @SuppressLint("SimpleDateFormat")
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun getSources(): Single<List<Source>> =
        apiInterface.getSources().map { it.sources }

    override fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>> =
        apiInterface.getTopHeadlines(Const.DEFAULT_LANG, page, pageSize).map { it.articles }

    override fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>> {
        return apiInterface.getArticles(
            searchParameters.sources.filter { it.selected }.joinToString(separator = ",") { it.id },
            searchParameters.from?.let { serverDateFormat.format(it.time) },
            searchParameters.to?.let { serverDateFormat.format(it.time) },
            searchParameters.sort.let {
                when (it) {
                    SearchParameters.Sort.POPULARITY -> "popularity"
                    else -> "published_at"
                }
            },
            page,
            pageSize
        ).map { it.articles }
    }

    override fun getFavorites(): Single<List<Article>> = throw UnsupportedOperationException()

    override fun saveArticleToFavorites(article: Article): Completable = throw UnsupportedOperationException()

    override fun removeArticleFromFavorites(article: Article): Completable = throw UnsupportedOperationException()
}