package com.lego.myadvanceapplication.data.models

data class Message(
    var id: String? = null,
    val message: String? = null,
    val user: String? = null,
    val time: String? = System.currentTimeMillis().toString(),
    val photoUrl: String? = null,
    val imageUrl: String? = null
)