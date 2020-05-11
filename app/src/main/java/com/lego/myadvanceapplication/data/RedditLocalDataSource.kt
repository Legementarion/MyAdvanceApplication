package com.lego.myadvanceapplication.data

import com.lego.myadvanceapplication.data.local.RedditNewsModel

interface RedditLocalDataSource {

    fun getTopNews(limit: Int, after: String?, before: String?): List<RedditNewsModel>

}