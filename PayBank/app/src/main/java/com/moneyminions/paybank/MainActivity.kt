package com.moneyminions.paybank

import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.moneyminions.paybank.ui.PayScreen
import com.moneyminions.paybank.ui.theme.PayBankTheme
import com.moneyminions.paybank.util.Constants
import com.moneyminions.paybank.util.Constants.CHANNEL_ID
import com.moneyminions.paybank.util.Constants.CHANNEL_NAME
import com.moneyminions.paybank.util.createNotificationChannel
import com.moneyminions.paybank.util.initFirebase
import com.moneyminions.paybank.viewmodel.PayViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    var fcmToken: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(notificationManager, CHANNEL_ID, CHANNEL_NAME)
//        fcmToken = initFirebase()
        initFirebase()

        setContent {
            PayBankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    PayScreen()
                }
            }
        }
    }
}

