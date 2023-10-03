package com.moneyminions.presentation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "NotificationUtils D210"
@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(notificationManager: NotificationManager, id: String, name: String) {
    Log.d(TAG, "createNotificationChannel...")
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(id, name, importance)
    notificationManager.createNotificationChannel(channel)
}