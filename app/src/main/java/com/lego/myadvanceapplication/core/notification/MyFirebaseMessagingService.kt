package com.lego.myadvanceapplication.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    companion object {
        private const val TAG = "MyFMService"
        private const val ENGAGE_TOPIC = "engage"
    }

    private val notificationController by inject<NotificationController>()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId())
        Log.d(
            TAG, "FCM Notification Message: " +
                    remoteMessage.getNotification()
        )
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM Token: $token")
        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance()
            .subscribeToTopic(ENGAGE_TOPIC)
    }

}