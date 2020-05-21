package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class GetHotNewsUseCase (private val repository: RedditRepository) {

    suspend fun getHotNews(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): RedditData {
        return repository.getHotNews(limit, after, before)
    }

}