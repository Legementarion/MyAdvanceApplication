package com.lego.myadvanceapplication.core.notification

import androidx.preference.PreferenceManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    companion object {
        private const val TAG = "MyAdvanceFMService"
        const val TOKEN_TOPIC = "token"
    }

    private val notificationController by inject<NotificationController>()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notificationController.createNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        FirebaseDatabase.getInstance().reference.child(TOKEN_TOPIC)
            .push().setValue(token)

        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            .putString(TOKEN_TOPIC, token).apply()
    }

}