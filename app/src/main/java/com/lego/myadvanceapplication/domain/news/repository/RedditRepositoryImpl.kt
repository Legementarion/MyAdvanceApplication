package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.data.RedditLocalDataSource
import com.lego.myadvanceapplication.data.RedditRemoteDataSource
import com.lego.myadvanceapplication.domain.news.RedditMapper
import com.lego.myadvanceapplication.domain.news.model.RedditData

class RedditRepositoryImpl(
    private val remoteSource: RedditRemoteDataSource,
    private val localSource: RedditLocalDataSource
) : RedditRepository {

    override suspend fun getHotNews(limit: Int, after: String?, before: String?): RedditData {
        return RedditMapper.toDomain(remoteSource.getHotNews(limit, after, before))
    }

    override suspend fun getTopNews(limit: Int, after: String?, before: String?): RedditData {
        return RedditMapper.toDomain(remoteSource.getTopNews(limit, after, before))
    }

    override suspend fun getNewNews(limit: Int, after: String?, before: String?): RedditData {
        return RedditMapper.toDomain(remoteSource.getNewNews(limit, after, before))
    }

    override suspend fun getFavoriteNews(limit: Int, after: String?, before: String?): RedditData {
        return RedditData(null, null, emptyList()) //todo implement
    }

    override suspend fun vote(id: Int, dir: Int) {
        remoteSource.vote(id, dir)
    }
}