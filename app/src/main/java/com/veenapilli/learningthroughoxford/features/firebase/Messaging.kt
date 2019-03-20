package com.veenapilli.learningthroughoxford.features.firebase


import android.content.Context
import android.util.Log
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.veenapilli.learningthroughoxford.R
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * Firebase Cloud Messaging (FCM) can be used to send messages to clients on iOS, Android and Web.
 *
 * This sample uses FCM to send two types of messages to clients that are subscribed to the `news`
 * topic. One type of message is a simple notification message (display message). The other is
 * a notification message (display notification) with platform specific customizations, for example,
 * a badge is added to messages that are sent to iOS devices.
 */
class Messaging {

    private val PROJECT_ID = "<YOUR-PROJECT-ID>"
    private val BASE_URL = "https://fcm.googleapis.com"
    private val FCM_SEND_ENDPOINT = "/v1/projects/$PROJECT_ID/messages:send"

    private val MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
    private val SCOPES = arrayOf(MESSAGING_SCOPE)

    private val TITLE = "FCM Notification"
    private val BODY = "Notification from FCM"
    val MESSAGE_KEY = "message"

    /**
     * Retrieve a valid access token that can be use to authorize requests to the FCM REST
     * API.
     *
     * @return Access token.
     * @throws IOException
     */

    // [START retrieve_access_token]
    private var accessToken = ""
    // [END retrieve_access_token]


    fun getAccessToken(context: Context) {
        var inputStream =
            context.resources.openRawResource(R.raw.firebase_admin_sdk)

        val googleCredential = GoogleCredential
            .fromStream(inputStream)
            .createScoped(Arrays.asList(*SCOPES))
        googleCredential.refreshToken()
        accessToken = googleCredential.accessToken
        Log.d("Message", "Key: $accessToken")

    }


    /**
     * Create HttpURLConnection that can be used for both retrieving and publishing.
     *
     * @return Base HttpURLConnection.
     * @throws IOException
     */
    private// [START use_access_token]
// [END use_access_token]
    val connection: HttpURLConnection
        @Throws(IOException::class)
        get() {
            val url = URL(BASE_URL + FCM_SEND_ENDPOINT)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty("Authorization", "Bearer $accessToken")
            httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8")
            return httpURLConnection
        }

    /**
     * Send request to FCM message using HTTP.
     *
     * @param fcmMessage Body of the HTTP request.
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun sendMessage(fcmMessage: JsonObject) {
        val connection = connection
        connection.doOutput = true
        val outputStream = DataOutputStream(connection.outputStream)
        outputStream.writeBytes(fcmMessage.toString())
        outputStream.flush()
        outputStream.close()

        val responseCode = connection.responseCode
        if (responseCode == 200) {
            val response = inputstreamToString(connection.inputStream)
            println("Message sent to Firebase for delivery, response:")
            println(response)
        } else {
            println("Unable to send message to Firebase:")
            val response = inputstreamToString(connection.errorStream)
            println(response)
        }
    }

    /**
     * Send a message that uses the common FCM fields to send a notification message to all
     * platforms. Also platform specific overrides are used to customize how the message is
     * received on Android and iOS.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun sendOverrideMessage() {
        val overrideMessage = buildOverrideMessage()
        println("FCM request body for override message:")
        prettyPrint(overrideMessage)
        sendMessage(overrideMessage)
    }

    /**
     * Build the body of an FCM request. This body defines the common notification object
     * as well as platform specific customizations using the android and apns objects.
     *
     * @return JSON representation of the FCM request body.
     */
    private fun buildOverrideMessage(): JsonObject {
        val jNotificationMessage = buildNotificationMessage()

        val messagePayload = jNotificationMessage.get(MESSAGE_KEY).asJsonObject
        messagePayload.add("android", buildAndroidOverridePayload())

        val apnsPayload = JsonObject()
        apnsPayload.add("headers", buildApnsHeadersOverridePayload())
        apnsPayload.add("payload", buildApsOverridePayload())

        messagePayload.add("apns", apnsPayload)

        jNotificationMessage.add(MESSAGE_KEY, messagePayload)

        return jNotificationMessage
    }

    /**
     * Build the android payload that will customize how a message is received on Android.
     *
     * @return android payload of an FCM request.
     */
    private fun buildAndroidOverridePayload(): JsonObject {
        val androidNotification = JsonObject()
        androidNotification.addProperty("click_action", "android.intent.action.MAIN")

        val androidNotificationPayload = JsonObject()
        androidNotificationPayload.add("notification", androidNotification)

        return androidNotificationPayload
    }

    /**
     * Build the apns payload that will customize how a message is received on iOS.
     *
     * @return apns payload of an FCM request.
     */
    private fun buildApnsHeadersOverridePayload(): JsonObject {
        val apnsHeaders = JsonObject()
        apnsHeaders.addProperty("apns-priority", "10")

        return apnsHeaders
    }

    /**
     * Build aps payload that will add a badge field to the message being sent to
     * iOS devices.
     *
     * @return JSON object with aps payload defined.
     */
    private fun buildApsOverridePayload(): JsonObject {
        val badgePayload = JsonObject()
        badgePayload.addProperty("badge", 1)

        val apsPayload = JsonObject()
        apsPayload.add("aps", badgePayload)

        return apsPayload
    }

    /**
     * Send notification message to FCM for delivery to registered devices.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun sendCommonMessage() {
        val notificationMessage = buildNotificationMessage()
        println("FCM request body for message using common notification object:")
        prettyPrint(notificationMessage)
        sendMessage(notificationMessage)
    }

    /**
     * Construct the body of a notification message request.
     *
     * @return JSON of notification message.
     */
    private fun buildNotificationMessage(): JsonObject {
        val jNotification = JsonObject()
        jNotification.addProperty("title", TITLE)
        jNotification.addProperty("body", BODY)

        val jMessage = JsonObject()
        jMessage.add("notification", jNotification)
        jMessage.addProperty("topic", "news")

        val jFcm = JsonObject()
        jFcm.add(MESSAGE_KEY, jMessage)

        return jFcm
    }

    /**
     * Read contents of InputStream into String.
     *
     * @param inputStream InputStream to read.
     * @return String containing contents of InputStream.
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun inputstreamToString(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val scanner = Scanner(inputStream)
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine())
        }
        return stringBuilder.toString()
    }

    /**
     * Pretty print a JsonObject.
     *
     * @param jsonObject JsonObject to pretty print.
     */
    private fun prettyPrint(jsonObject: JsonObject) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        println(gson.toJson(jsonObject) + "\n")
    }

    /* @Throws(IOException::class)
     @JvmStatic
     fun main(args: Array<String>) {
         if (args.size == 1 && args[0] == "common-message") {
             sendCommonMessage()
         } else if (args.size == 1 && args[0] == "override-message") {
             sendOverrideMessage()
         } else {
             System.err.println("Invalid command. Please use one of the following commands:")
             // To send a simple notification message that is sent to all platforms using the common
             // fields.
             System.err.println("./gradlew run -Pmessage=common-message")
             // To send a simple notification message to all platforms using the common fields as well as
             // applying platform specific overrides.
             System.err.println("./gradlew run -Pmessage=override-message")
         }
     }*/

}
