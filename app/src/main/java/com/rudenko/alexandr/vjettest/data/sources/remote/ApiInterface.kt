package com.rudenko.alexandr.vjettest.data.sources.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("sources")
    fun getSources(): Single<Response>

    @GET("top-headlines")
    fun getTopHeadlines(
            @Query("language") language: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int
    ): Single<Response>

    @GET("everything")
    fun getArticles(
            @Query("sources") sources: String,
            @Query("from") from: String?,
            @Query("to") to: String?,
            @Query("sortBy") sort: String?,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int
    ): Single<Response>


}