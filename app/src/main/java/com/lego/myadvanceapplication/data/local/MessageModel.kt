package com.lego.myadvanceapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageModel(
    @PrimaryKey
    val message: String = ""
)