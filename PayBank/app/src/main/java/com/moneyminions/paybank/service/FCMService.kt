package com.moneyminions.paybank.service

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moneyminions.paybank.MainActivity
import com.moneyminions.paybank.R
import com.moneyminions.paybank.util.Constants
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

private const val TAG = "FCMService D210"
class FCMService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        var messageTitle = ""
        var messageBody = ""
        var notificationId = (System.currentTimeMillis()).toInt()
//        var articleId = ""
//        var type = ""

        // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        messageTitle = data["title"].toString()
        messageBody = data["message"].toString()
//        type = data[Constants.TYPE] ?: TYPE_REPORT
//        articleId = if (type == TYPE_REPORT) data[Constants.REPORT_ID].toString() else data[Constants.ARTICLE_ID].toString()
        val mainIntent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            putExtra(Constants.TYPE, type)
//            putExtra(Constants.ARTICLE_ID, articleId)
        }

        val mainPendingIntent: PendingIntent =
            PendingIntent.getActivity(
                this,
                notificationId,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


        // 알림에 대한 UI 정보, 작업
        val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setSmallIcon(R.drawable.paybank_logo)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setContentIntent(mainPendingIntent)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .setFullScreenIntent(mainPendingIntent, true)


        NotificationManagerCompat.from(this).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
//                    Log.d(TAG, "sendNotification permission not allowed")
                    return
                }
            }
            Log.d(TAG, "notify...")
            notify(notificationId, notificationBuilder.build())
        }
    }


}
