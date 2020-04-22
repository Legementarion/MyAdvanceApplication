package com.lego.myadvanceapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class RedditNewsModel(
    @PrimaryKey
    val id: Long? = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "desc") val description: String?
)