package com.lego.myadvanceapplication.domain.news

import com.lego.myadvanceapplication.data.models.NewsResponse
import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.model.RedditPost

object RedditMapper {

    fun toDomain(topNews: NewsResponse): RedditData {
        val list = mutableListOf<RedditPost>()
        topNews.listingData.children.forEach {
            list.add(
                RedditPost(
                    it.post.id,
                    it.post.title,
                    it.post.author,
                    it.post.subreddit,
                    it.post.score,
                    it.post.numComments,
                    it.post.created,
                    it.post.thumbnail,
                    it.post.url,
                    it.post.permalink
                )
            )
        }
        return RedditData(topNews.listingData.before, topNews.listingData.after, list)
    }

}