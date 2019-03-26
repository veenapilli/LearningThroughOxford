package com.veenapilli.learningthroughoxford.features.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.veenapilli.learningthroughoxford.R
import com.veenapilli.learningthroughoxford.features.collapsing.justactivitiy.CollapsibleToolbarActivity
import com.veenapilli.learningthroughoxford.features.oxford.fragments.MeaningFragment

class NotificationHelper {

    private lateinit var builder: NotificationCompat.Builder

    fun notificationHelper(route: String, context: Context, remoteMessage: RemoteMessage): NotificationCompat.Builder {
        val pendingIntent = PendingIntent.getActivity(context, 0, routingHelper(route, context), 0)

        when (route) {
            "type1" -> {
                createNotificationChannel(
                    context,
                    CHANNEL_ID_TYPE_1,
                    context.resources.getString(R.string.channel_name_1),
                    context.resources.getString(R.string.channel_description_1)
                )
                remoteMessage.notification?.let {
                    Log.d(LocalFirebaseService.TAG, "Message Notification Body: " + it.body)
                    builder = getNotificationBuilder(
                        context,
                        CHANNEL_ID_TYPE_1,
                        R.drawable.common_google_signin_btn_icon_dark,
                        it.title,
                        it.body,
                        NotificationCompat.PRIORITY_DEFAULT, pendingIntent
                    )

                }

            }
            "type2" -> {
                createNotificationChannel(
                    context,
                    CHANNEL_ID_TYPE_2,
                    context.resources.getString(R.string.channel_name_2),
                    context.resources.getString(R.string.channel_description_2)
                )
                remoteMessage.notification?.let {
                    Log.d(LocalFirebaseService.TAG, "Message Notification Body: " + it.body)
                    builder = getNotificationBuilder(
                        context,
                        CHANNEL_ID_TYPE_2,
                        R.drawable.common_google_signin_btn_icon_dark,
                        it.title,
                        it.body,
                        NotificationCompat.PRIORITY_DEFAULT,
                        pendingIntent
                    )
                }
            }
            "type3" -> {
                createNotificationChannel(
                    context,
                    CHANNEL_ID_TYPE_3,
                    context.resources.getString(R.string.channel_name_3),
                    context.resources.getString(R.string.channel_description_3)
                )
                remoteMessage.notification?.let {
                    Log.d(LocalFirebaseService.TAG, "Message Notification Body: " + it.body)
                    builder = getNotificationBuilder(
                        context,
                        CHANNEL_ID_TYPE_3,
                        R.drawable.common_google_signin_btn_icon_dark,
                        it.title,
                        it.body,
                        NotificationCompat.PRIORITY_DEFAULT,
                        pendingIntent
                    )
                }
            }
            else -> {
                // Default notification if we do not support a specific source
                // Set to a default channel
                builder = getNotificationBuilder(
                    context,
                    CHANNEL_ID_DEFAULT,
                    R.drawable.common_google_signin_btn_icon_dark,
                    "Default Notification",
                    "Default Body",
                    NotificationCompat.PRIORITY_DEFAULT,
                    pendingIntent
                )
            }
        }
        return builder
    }

    fun routingHelper(route: String, context: Context): Intent {
        return when (route) {
            "type1" -> Intent(context, MeaningFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            "type2" -> Intent(context, MeaningFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            "type3" -> Intent(context, CollapsibleToolbarActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            else -> Intent(context, CollapsibleToolbarActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }

    private fun getNotificationBuilder(
        context: Context,
        channelId: String,
        icon: Int,
        title: String?,
        body: String?,
        priority: Int,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(priority)
            .setGroup(GROUP_KEY_EMAIL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

    }

    private fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        channelDescription: String
    ) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        val TAG: String = LocalFirebaseService::class.java.name
        const val CHANNEL_ID_DEFAULT: String = "1234567890_default"
        const val CHANNEL_ID_TYPE_1: String = "1234567890_type_1"
        const val CHANNEL_ID_TYPE_2: String = "1234567890_type_2"
        const val CHANNEL_ID_TYPE_3: String = "1234567890_type_3"
        val GROUP_KEY_EMAIL = "group_email"

    }
}