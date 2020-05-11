package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse

interface RedditRemoteDataSource {

    suspend fun getTopNews(limit: Int, after: String?, before: String?): NewsResponse

}