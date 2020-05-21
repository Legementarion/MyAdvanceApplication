package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.domain.news.model.RedditData

interface RedditRepository {

    suspend fun getHotNews(limit: Int, after: String?, before: String?): RedditData

    suspend fun getTopNews(limit: Int, after: String?, before: String?): RedditData

    suspend fun getNewNews(limit: Int, after: String?, before: String?): RedditData

    suspend fun getFavoriteNews(limit: Int, after: String?, before: String?): RedditData

    suspend fun vote(id: Int, dir: Int)

}