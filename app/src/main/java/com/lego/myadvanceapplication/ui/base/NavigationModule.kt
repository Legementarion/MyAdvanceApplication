package com.lego.myadvanceapplication.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.lego.myadvanceapplication.ui.chat.ChatPushActivity
import com.lego.myadvanceapplication.ui.main.MainActivity
import com.lego.myadvanceapplication.ui.news.list.RedditNewsListActivity
import com.lego.myadvanceapplication.ui.settings.SettingsActivity
import com.lego.myadvanceapplication.ui.signin.SignInActivity

fun AppCompatActivity.navigateToSettings() {
    startActivity(Intent(this, SettingsActivity::class.java))
}

fun AppCompatActivity.navigateToFeed() {
    startActivity(Intent(this, RedditNewsListActivity::class.java))
}

fun AppCompatActivity.navigateToChat() {
    startActivity(Intent(this, ChatPushActivity::class.java))
}

fun AppCompatActivity.navigateToMain() {
    startActivity(Intent(this, MainActivity::class.java))
}

fun AppCompatActivity.navigateToSignIn() {
    startActivity(Intent(this, SignInActivity::class.java))
}