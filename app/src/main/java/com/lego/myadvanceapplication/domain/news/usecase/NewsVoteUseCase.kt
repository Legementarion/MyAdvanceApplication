package com.lego.myadvanceapplication.domain.news.usecase

import com.lego.myadvanceapplication.domain.news.repository.RedditRepository

class NewsVoteUseCase(private val repository: RedditRepository) {

    suspend fun vote() {

    }
}