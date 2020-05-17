package com.lego.myadvanceapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RedditNewsModel::class, MessageModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditDao(): RedditDao
}
