package com.lego.myadvanceapplication.domain.news.repository

import com.lego.myadvanceapplication.data.RedditLocalDataSource
import com.lego.myadvanceapplication.data.RedditRemoteDataSource

class RedditRepositoryImpl(
    remoteSource: RedditRemoteDataSource,
    localSource: RedditLocalDataSource
) : RedditRepository {

}