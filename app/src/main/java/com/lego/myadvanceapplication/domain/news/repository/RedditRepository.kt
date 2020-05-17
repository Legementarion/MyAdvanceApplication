package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.domain.news.model.RedditData

interface RedditRepository {

    suspend fun getTopNews(limit: Int, after: String?, before: String?): RedditData

}