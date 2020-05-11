package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    suspend fun getTopNews(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): NewsResponse

    @POST
    fun getHotNews()

    @POST
    fun authorize()

}