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
    @SerializedName("preview") val preview: Media?,
    @SerializedName("is_video") val isVideo: Boolean
)

data class RedditVideo(
    @SerializedName("is_gif") val isGif: Boolean,
    @SerializedName("fallback_url") val url: String
)

data class Media(
    @SerializedName("images") val images: List<RedditImageSource>?,
    @SerializedName("reddit_video_preview") val redditVideo: RedditVideo
)

data class RedditImageSource(
    val source: ImageSource?
)

data class ImageSource(
    val url: String,
    val width: Int,
    val height: Int
)