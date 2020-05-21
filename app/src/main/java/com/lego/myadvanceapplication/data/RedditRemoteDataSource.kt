package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse

interface RedditRemoteDataSource {

    suspend fun getHotNews(limit: Int, after: String?, before: String?): NewsResponse

    suspend fun getTopNews(limit: Int, after: String?, before: String?): NewsResponse

    suspend fun getNewNews(limit: Int, after: String?, before: String?): NewsResponse

    suspend fun vote(id: Int, dir: Int)

}