package com.lego.myadvanceapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RedditDao {

    @Query("SELECT * FROM news")
    fun getAll(): List<RedditNewsModel>

    @Insert
    fun insertAll(vararg news: RedditNewsModel)

    @Delete
    fun delete(news: RedditNewsModel)

}