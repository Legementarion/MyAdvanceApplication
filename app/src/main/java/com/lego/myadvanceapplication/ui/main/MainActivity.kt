package com.lego.myadvanceapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.core.notification.NotificationController
import com.lego.myadvanceapplication.ui.base.navigateToChat
import com.lego.myadvanceapplication.ui.base.navigateToFeed
import com.lego.myadvanceapplication.ui.base.navigateToSettings
import com.lego.myadvanceapplication.ui.base.navigateToSignIn
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelBasic: BasicMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelBasic = BasicMainViewModel()

        settingsBtn.setOnClickListener {
            navigateToSettings()
        }

        feedBtn.setOnClickListener {
            navigateToFeed()
        }

        chatBtn.setOnClickListener {
            navigateToChat()
        }

        profileBtn.setOnClickListener {
            navigateToSignIn()
        }

        val notificationController = NotificationController(this)

        testBtn.setOnClickListener {
            notificationController.createNotification(RemoteMessage(Bundle().apply {
                putString("title","from: Denko")
                putString("body","message")
                putString("icon","https://lh3.googleusercontent.com/ogw/ADGmqu_zpF8xahIfF1MUWvzm8V7rx0ImusjL7-NcTMCQ=s64-c-mo")
            }))
        }

    }

}
