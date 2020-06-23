package com.lego.myadvanceapplication.core.notification

import android.util.Log
import androidx.preference.PreferenceManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    companion object {
        private const val TAG = "MyFMService"
        private const val ENGAGE_TOPIC = "engage"
        const val TOKEN_TOPIC = "token"
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
        FirebaseDatabase.getInstance().reference.child(TOKEN_TOPIC)
            .push().setValue(token)

        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString(TOKEN_TOPIC, token).apply()

        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance()
            .subscribeToTopic(ENGAGE_TOPIC)
    }

}