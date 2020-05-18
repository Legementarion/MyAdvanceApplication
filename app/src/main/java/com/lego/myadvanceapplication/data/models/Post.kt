package com.lego.myadvanceapplication.data.models

import com.google.gson.annotations.SerializedName

data class Post(
    val id: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val score: Int,
    @SerializedName("num_comments") val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String,
    val permalink: String,
    @SerializedName("is_video") val isVideo: Boolean,
    @SerializedName("media") val media: Media
)

data class Media(
    @SerializedName("reddit_video") val redditVideo: RedditVideo
)

data class RedditVideo(
    @SerializedName("is_gif") val isGif: Boolean,
    @SerializedName("fallback_url") val url: String
)