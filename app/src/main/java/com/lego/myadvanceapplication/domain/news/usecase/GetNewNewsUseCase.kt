package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class GetNewNewsUseCase (private val repository: RedditRepository) {

    suspend fun getNewNews(
        limit: Int,
        after: String? = null,
        before: String? = null
    ): RedditData {
        return repository.getNewNews(limit, after, before)
    }

}