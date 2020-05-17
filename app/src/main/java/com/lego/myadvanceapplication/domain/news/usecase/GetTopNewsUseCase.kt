package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class GetTopNewsUseCase(private val repository: RedditRepository) {

    suspend fun getTopNews(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): RedditData {
        return repository.getTopNews(limit, after, before)
    }

}