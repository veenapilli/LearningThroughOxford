package com.veenapilli.learningthroughoxford.features.firebase

import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class LocalFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.let { message ->
            Log.d(TAG, "From: " + message.from)
            // Check if message contains a data payload.
            message.data?.let { messageData ->
                Log.d(TAG, "Message Notification data payload: $messageData")
                val data = JSONObject(messageData)
                val route = data.getString("source")
                val builder = NotificationHelper().notificationHelper(route, this, remoteMessage)


                // Sample summary notification.
                // Can be customized based on the number of outstanding notifications which are available in the DB
                // Correspondingly we may hide the other notifications when the summary is displayed
                /*val summaryBuilder = getSummaryNotificationBuilder(
                    this,
                    CHANNEL_ID_TYPE_3,
                    R.drawable.common_google_signin_btn_icon_dark,
                    "Summary",
                    "Summary Body",
                    NotificationCompat.PRIORITY_DEFAULT
                )*/
                with(NotificationManagerCompat.from(this)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(NotificationId.id, builder.build())
                    //notify(NotificationId.id, summaryBuilder.build())
                }
            }
        }
    }


    /*private fun getSummaryNotificationBuilder(
        context: Context,
        channelId: String,
        icon: Int,
        title: String?,
        body: String?,
        priority: Int
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            // Displays the summary title and content when the notification is rendered and the user has not interacted with the message
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(priority)
            .setGroup(GROUP_KEY_EMAIL)
            .setGroupSummary(true)
            .setStyle(
                // Adds the inner messages when the summary is expanded
                NotificationCompat.InboxStyle()
                    .addLine("Alex Faarborg Check this out")
                    .addLine("Jeff Chang Launch Party")
                    .setBigContentTitle("2 new messages")
                    .setSummaryText("janedoe@example.com")

            )
            .setAutoCancel(true)

    }
*/
    companion object {
        val TAG: String = LocalFirebaseService::class.java.name
    }
}
