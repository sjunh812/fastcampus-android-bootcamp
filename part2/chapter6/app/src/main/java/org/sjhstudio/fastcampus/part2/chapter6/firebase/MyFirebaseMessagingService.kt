package org.sjhstudio.fastcampus.part2.chapter6.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.sjhstudio.fastcampus.part2.chapter6.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        private const val LOG = "MyFirebaseMessagingService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e(LOG, "onNewToken(): $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()

        message.notification?.let { messageNotification ->
            if (messageNotification.title != null && messageNotification.body != null) {
                createNotification(messageNotification.title!!, messageNotification.body!!)
            }
        }
    }

    private fun createNotificationChannel(): NotificationChannel {
        val name = getString(R.string.notification_channel_name)
        val description = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel =
            NotificationChannel(getString(R.string.notification_channel_id), name, importance)
                .apply { setDescription(description) }

        notificationManager.createNotificationChannel(notificationChannel)

        return notificationChannel
    }

    private fun createNotification(title: String, content: String) {
        val notification = NotificationCompat.Builder(
            applicationContext,
            getString(R.string.notification_channel_id)
        )
            .setSmallIcon(R.drawable.ic_chat_bubble_24)
            .setContentTitle(title)
            .setContentText(content)
            .build()

        notificationManager.notify(0, notification)
    }
}