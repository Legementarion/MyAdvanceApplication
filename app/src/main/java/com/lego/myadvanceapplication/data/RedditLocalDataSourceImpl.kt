package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.local.RedditDao
import com.lego.myadvanceapplication.data.local.RedditNewsModel

class RedditLocalDataSourceImpl(private val redditDao: RedditDao): RedditLocalDataSource {

    override fun getTopNews(limit: Int, after: String?, before: String?): List<RedditNewsModel> {
        return redditDao.getAll()
    }
}