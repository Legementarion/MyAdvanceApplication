package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.data.RedditLocalDataSource
import com.lego.myadvanceapplication.data.RedditRemoteDataSource
import com.lego.myadvanceapplication.domain.news.RedditMapper
import com.lego.myadvanceapplication.domain.news.model.RedditPost

class RedditRepositoryImpl(
    private val remoteSource: RedditRemoteDataSource,
    private val localSource: RedditLocalDataSource
) : RedditRepository {

    override suspend fun getTopNews(limit: Int, after: String?, before: String?): List<RedditPost> {
        return RedditMapper.toDomain(remoteSource.getTopNews(limit, after, before))
    }

}