package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse

class RedditRemoteDataSourceImpl(private val api: RedditApi) : RedditRemoteDataSource {

    override suspend fun getHotNews(limit: Int, after: String?, before: String?): NewsResponse {
        return api.getHotNews(limit, after, before)
    }

    override suspend fun getTopNews(limit: Int, after: String?, before: String?): NewsResponse {
        return api.getTopNews(limit, after, before)
    }

    override suspend fun getNewNews(limit: Int, after: String?, before: String?): NewsResponse {
        return api.getNewNews(limit, after, before)
    }
}