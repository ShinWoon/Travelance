package com.moneyminions.paybank.util

import android.os.Build

object Permissions {    // 사용할 권한 정의
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
//        Manifest.permission.POST_NOTIFICATIONS
    } else {
    }

}
