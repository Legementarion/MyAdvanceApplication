package com.lego.myadvanceapplication.domain.news

import android.text.Html
import android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
import com.lego.myadvanceapplication.data.models.NewsResponse
import com.lego.myadvanceapplication.domain.news.model.RedditData
import com.lego.myadvanceapplication.domain.news.model.RedditPost

object RedditMapper {

    fun toDomain(topNews: NewsResponse): RedditData {
        val list = mutableListOf<RedditPost>()
        topNews.listingData.children.forEach {
            var imageUrl: String? = null
            it.post.preview?.images?.first()?.source?.url?.let { url ->
                imageUrl = Html.fromHtml(url, TO_HTML_PARAGRAPH_LINES_CONSECUTIVE).toString()
            }

            val isGif = it.post.preview?.redditVideo?.isGif ?: false
            val gifUrl: String? = it.post.preview?.redditVideo?.url

            list.add(
                RedditPost(
                    it.post.id,
                    it.post.title,
                    it.post.ороооро,
                    it.post.subreddit,
                    it.post.score,
                    it.post.numComments,
                    it.post.created,
                    it.post.thumbnail,
                    imageUrl,
                    it.post.url,
                    it.post.permalink,
                    it.post.isVideo,
                    isGif,
                    gifUrl
                )
            )
        }
        return RedditData(topNews.listingData.before, topNews.listingData.after, list)
    }

}