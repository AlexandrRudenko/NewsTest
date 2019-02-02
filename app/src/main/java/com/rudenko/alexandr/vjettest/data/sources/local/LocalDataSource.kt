package com.rudenko.alexandr.vjettest.data.sources.local

import com.rudenko.alexandr.vjettest.data.Article
import com.rudenko.alexandr.vjettest.data.Source
import com.rudenko.alexandr.vjettest.data.sources.DataSource
import com.rudenko.alexandr.vjettest.data.sources.SearchParameters
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException
import java.util.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val favoritesDao: FavoritesDao
) : DataSource {

    override fun getSources(): Single<List<Source>> {
        throw UnsupportedOperationException()
    }

    override fun getTopHeadlines(page: Int, pageSize: Int): Single<List<Article>> {
        throw UnsupportedOperationException()
    }

    override fun getArticles(searchParameters: SearchParameters, page: Int, pageSize: Int): Single<List<Article>> {
        throw UnsupportedOperationException()
    }

    override fun getFavorites(): Single<List<Article>> =
        favoritesDao.getAll().map { favoritesToArticle(it) }

    override fun saveArticleToFavorites(article: Article): Completable =
        Completable.fromAction {favoritesDao.insertArticle(articleToFavorite(article))}

    override fun removeArticleFromFavorites(article: Article): Completable =
        Completable.fromAction {favoritesDao.deleteArticle(articleToFavorite(article))}

    private fun favoritesToArticle(favorites: List<FavoriteArticle>): List<Article> =
        favorites.map {favoriteToArticle(it)}

    private fun favoriteToArticle(favorite: FavoriteArticle): Article {
         favorite.apply {
             return Article(
                source,
                author,
                title,
                description,
                url,
                urlToImage,
                publishedAt
            )
        }
    }

    private fun articleToFavorite(article: Article): FavoriteArticle {
        article.apply {
            return FavoriteArticle(
                source,
                author,
                title,
                description,
                url,
                urlToImage,
                publishedAt,
                Date().time
            )
        }
    }
}