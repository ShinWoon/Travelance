package com.moneyminions.paybank.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "NotificationUtils D210"

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(notificationManager: NotificationManager, id: String, name: String) {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(id, name, importance)
    notificationManager.createNotificationChannel(channel)
}

fun initFirebase(): String {
    var token: String = ""
    FirebaseMessaging.getInstance().token.addOnCompleteListener {
        if (!it.isSuccessful) {
            // 토큰 요청 task가 실패 한 경우 처리
            return@addOnCompleteListener
        }
        // 토큰 요청 task가 성공한 경우 task의 result에 token 값이 내려온다.
        token = it.result
        Constants.fcmToken = token
        Log.d(TAG, "initFirebase: $token")
        Log.d(TAG, "Constants의 fcmToken: ${Constants.fcmToken}")
    }
    return token
}