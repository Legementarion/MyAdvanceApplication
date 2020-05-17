package com.lego.myadvanceapplication.data.models

data class Post(
    val id: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val score: Int,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String,
    val permalink: String
)