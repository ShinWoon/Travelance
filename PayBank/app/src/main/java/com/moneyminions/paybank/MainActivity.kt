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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.moneyminions.paybank.ui.theme.PayBankTheme
import com.moneyminions.paybank.util.Constants.CHANNEL_ID
import com.moneyminions.paybank.util.Constants.CHANNEL_NAME
import com.moneyminions.paybank.util.createNotificationChannel
import com.moneyminions.paybank.util.initFirebase

private const val TAG = "MainActivity D210"
class MainActivity : ComponentActivity() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(notificationManager, CHANNEL_ID, CHANNEL_NAME)
        initFirebase()

        setContent {
            PayBankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    PayScreen(PayViewModel())
                }
            }
        }
    }
}

