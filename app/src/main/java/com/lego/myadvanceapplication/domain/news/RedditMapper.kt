package com.lego.myadvanceapplication.domain.news

import com.lego.myadvanceapplication.data.models.NewsResponse
import com.lego.myadvanceapplication.domain.news.model.RedditPost

object RedditMapper {

    fun toDomain(topNews: NewsResponse): MutableList<RedditPost> {
        val list = mutableListOf<RedditPost>()
        topNews.listingData.children.forEach {
            list.add(RedditPost(it.post.id))
        }
        return list
    }

}