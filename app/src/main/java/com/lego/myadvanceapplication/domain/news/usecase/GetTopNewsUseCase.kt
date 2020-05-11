package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.model.RedditPost
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class GetTopNewsUseCase(private val repository: RedditRepository) {

    suspend fun getTopNews(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): List<RedditPost> {
        return repository.getTopNews(limit, after, before)
    }

}