package com.moneyminions.data.service

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moneyminions.data.R
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.internal.notify
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

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
        var messageContent = ""
        var roomId: Int
        var paymentId: Long
        var notificationId = (System.currentTimeMillis()).toInt()

        // background 에 있을경우 혹은 foreground에 있을경우 두 경우 모두
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        Log.d(TAG, "paymentId : ${data["paymentId"]}")
        if(data["paymentId"] != "0") {
            messageTitle = "공금에 등록하시겠습니까?"
            messageContent = data["message"]!!
            Log.d(TAG, "sendNotification messageContent : $messageContent")
            paymentId = data["paymentId"]!!.toLong()

//        type = data[Constants.TYPE] ?: TYPE_REPORT
//        articleId = if (type == TYPE_REPORT) data[Constants.REPORT_ID].toString() else data[Constants.ARTICLE_ID].toString()
            val mainIntent =
                Intent(this, Class.forName("com.moneyminions.presentation.MainActivity")).apply {
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

            // 확인 버튼을 눌렀을 때의 이벤트 처리
            val confirmIntent =
                Intent(this, Class.forName("com.moneyminions.data.service.ConfirmReceiver")).apply {
                    putExtra("ACTION_TYPE", "CONFIRM")
                    putExtra("PAYMENT_ID", paymentId)
                    putExtra("NOTIFICATION_ID", notificationId)
                }
            val confirmPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(
                    this,
                    notificationId,
                    confirmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )


            // 취소 버튼을 눌렀을 때 아무 일도 일어나지 않게 처리
            val cancelIntent =
                Intent(this, Class.forName("com.moneyminions.data.service.CancelReceiver")).apply {
                    putExtra("NOTIFICATION_ID", notificationId)
                }
            val cancelPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(
                    this,
                    notificationId,
                    cancelIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )


            // 알림에 대한 UI 정보, 작업
            val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.travelance_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
                .setContentIntent(mainPendingIntent)
                .setGroupSummary(true)
                .setAutoCancel(true)
                .setFullScreenIntent(mainPendingIntent, true)
                .addAction(
                    com.google.android.material.R.drawable.ic_search_black_24,
                    "확인", // 버튼 텍스트
                    confirmPendingIntent // 확인 버튼 클릭 시 PendingIntent
                )
                .addAction(
                    com.google.android.material.R.drawable.ic_arrow_back_black_24, // 취소 버튼 아이콘
                    "취소", // 버튼 텍스트
                    cancelPendingIntent // 취소 버튼 클릭 시 PendingIntent
                )

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
                Log.d(TAG, "notify... $notificationId")
//            notify(101, notificationBuilder.build())
                notify(notificationId, notificationBuilder.build())
            }
        }else{
            roomId = data["message"]!!.toInt()
            Log.d(TAG, "sendNotification roomId : $roomId")
            messageTitle = "정산 완료 알림!"
            messageContent = "정산이 완료되었습니다. 확인해보세요!"
            val mainIntent =
                Intent(this, Class.forName("com.moneyminions.presentation.MainActivity")).apply {
                    putExtra("type", "settle finish")
                    putExtra("roomId", roomId)
                }

            val mainPendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    this,
                    notificationId,
                    mainIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.travelance_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
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
                Log.d(TAG, "notify... $notificationId")
//            notify(101, notificationBuilder.build())
                notify(notificationId, notificationBuilder.build())
            }
        }
    }


}

class ConfirmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive... ${intent?.extras?.getString("ACTION_TYPE")}")
        if (intent?.extras?.getString("ACTION_TYPE") == "CONFIRM") {
            // "확인" 버튼을 눌렀을 때 원하는 동작을 수행합니다.
            Log.d(TAG, "확인 버튼을 눌렀습니다 paymentId : ${intent?.extras?.getLong("PAYMENT_ID")}")
            // 여기에 원하는 동작을 추가하세요.
            runBlocking {
                val result = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BusinessService::class.java).updateFCMPaymentInfo(
                        TravelPaymentChangeInfoRequest(paymentId = intent?.extras?.getLong("PAYMENT_ID")!!.toInt(), true)
                    )
                if(result.isSuccessful){
                    Log.d(TAG, "onReceive: $result")
                    val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationId = intent?.getIntExtra("NOTIFICATION_ID", 0) ?: 0

                    // 알림을 제거합니다.
                    notificationManager.cancel(notificationId)
                    Log.d(TAG, "onReceive: $notificationId")
                }
            }

        }
    }
}
class CancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent?.getIntExtra("NOTIFICATION_ID", 0) ?: 0

        // 알림을 제거합니다.
        notificationManager.cancel(notificationId)
        Log.d(TAG, "onReceive: $notificationId")
    }
}
