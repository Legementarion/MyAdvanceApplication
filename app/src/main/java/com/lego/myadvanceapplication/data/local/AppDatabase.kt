package com.lego.myadvanceapplication.data.local

import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {
    abstract fun redditDao(): RedditDao
}
