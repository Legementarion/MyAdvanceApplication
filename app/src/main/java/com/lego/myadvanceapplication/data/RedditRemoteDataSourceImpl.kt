package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.models.NewsResponse

class RedditRemoteDataSourceImpl(val api: RedditApi) : RedditRemoteDataSource {

    override suspend fun getTopNews(limit: Int, after: String?, before: String?): NewsResponse {
        return api.getTopNews(limit, after, before)
    }
}