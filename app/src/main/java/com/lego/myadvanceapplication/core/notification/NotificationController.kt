package com.lego.myadvanceapplication.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.request.target.NotificationTarget
import com.google.firebase.messaging.RemoteMessage
import com.lego.myadvanceapplication.R
import com.lego.myadvanceapplication.ui.chat.ChatPushActivity
import com.lego.myadvanceapplication.ui.utils.loadBitmap
import java.util.*


class NotificationController(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "AdvanceChannelID"
        private const val CHANNEL_NAME = "AdvanceChannel"
        private const val GROUP_KEY_TODO = "com.lego.myadvanceapplication"

    }

    fun createNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"] ?: ""
        val message = remoteMessage.data["body"] ?: ""
        val icon = remoteMessage.data["icon"] ?: ""

        val notificationId = Random().nextInt()

        val remoteView = RemoteViews(context.packageName, R.layout.remoteview_notification).apply {
            setImageViewResource(R.id.icon, R.drawable.ic_star)
            setTextViewText(R.id.remoteview_notification_headline, title)
            setTextViewText(R.id.remoteview_notification_short_message, message)
        }


        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, ChatPushActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        createChannel()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_star)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setGroup(GROUP_KEY_TODO)
            .setContent(remoteView)
            .setAutoCancel(true)

        val notification = builder.build()

        val target = NotificationTarget(
            context,
            R.id.icon,
            remoteView,
            notification,
            notificationId
        )

        target.loadBitmap(icon, context)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notification)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Todo notification"

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

}