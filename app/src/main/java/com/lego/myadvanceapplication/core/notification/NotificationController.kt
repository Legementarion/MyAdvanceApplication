package com.lego.myadvanceapplication.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lego.myadvanceapplication.R

class NotificationController(private val context: Context) {

    companion object {
        private val CHANNEL_ID = ""
    }

    //todo icon and titles for notifications
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = context.getString(R.string.channel_name)
//            val descriptionText = context.getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
    }

    fun createNotification() {
//        // Create an explicit intent for an Activity in your app
//        val intent = Intent(context, AlertDetails::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle("My notification")
//            .setContentText("Hello World!")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(context)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(notificationId, builder.build())
//        }
    }

    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
//        val builder = NotificationCompat.Builder(
//            applicationContext,
//            applicationContext.getString(R.string.egg_notification_channel_id)
//        ).setSmallIcon(R.drawable.cooked_egg)
//            .setContentTitle(applicationContext.getString(R.string.notification_title))
//            .setContentText(messageBody)
//        notify(NOTIFICATION_ID, builder.build())

    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_LOW
            )
            // TODO: Step 2.6 disable badges for this channel

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        // TODO: Step 1.6 END create channel
    }

}