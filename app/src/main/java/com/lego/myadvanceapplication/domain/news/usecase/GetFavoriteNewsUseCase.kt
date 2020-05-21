package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class GetFavoriteNewsUseCase(private val repository: RedditRepository) {

    suspend fun getFavoriteNews(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): RedditData {
        return repository.getFavoriteNews(limit, after, before)
    }

}