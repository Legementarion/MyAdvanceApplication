package com.lego.myadvanceapplication.domain.news.model

data class RedditData(
    val before: String?,
    val after: String?,
    val posts: List<RedditPost>
) {
    fun isEmpty(): Boolean {
        return before == null && after == null && posts.isEmpty()
    }
}

data class RedditPost(
    val id: String = "",
    val title: String,
    val author: String,
    val subreddit: String,
    val score: Int,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String,
    val permalink: String,
    val isVideo: Boolean
)