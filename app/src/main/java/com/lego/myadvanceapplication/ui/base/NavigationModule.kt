package com.lego.myadvanceapplication.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.lego.myadvanceapplication.ui.main.MainActivity
import com.lego.myadvanceapplication.ui.news.list.RedditNewsListActivity
import com.lego.myadvanceapplication.ui.settings.SettingsActivity

fun AppCompatActivity.navigateToSettings() {
    startActivity(Intent(this, SettingsActivity::class.java))
}

fun AppCompatActivity.navigateToFeed() {
    startActivity(Intent(this, RedditNewsListActivity::class.java))
}

fun AppCompatActivity.navigateToMain() {
    startActivity(Intent(this, MainActivity::class.java))
}
