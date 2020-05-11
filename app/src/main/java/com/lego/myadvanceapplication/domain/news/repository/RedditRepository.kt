package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.domain.news.model.RedditPost

interface RedditRepository {

    suspend fun getTopNews(limit: Int, after: String?, before: String?): List<RedditPost>

}