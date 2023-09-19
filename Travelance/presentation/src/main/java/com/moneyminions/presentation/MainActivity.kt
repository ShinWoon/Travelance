package com.moneyminions.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kakao.sdk.common.util.Utility
import com.moneyminions.presentation.screen.MainScreen
import com.moneyminions.presentation.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MainScreen(rememberAnimatedNavController())

//                var keyHash = Utility.getKeyHash(this)
//                Log.d(TAG, "Kakao HashKey : $keyHash")


                /**
                 * 카카오 공유 API 반환 값 수신
                 */
                if (Intent.ACTION_VIEW == intent.action) {
                    val uri = intent.data
                    if (uri != null) {
                        Log.d(TAG, "onCreate: ${uri.getQueryParameter("number")} / ${uri.getQueryParameter("route")} /${uri.getQueryParameter("data")}")
                        uri.getQueryParameter("number")
                        uri.getQueryParameter("route")
                        uri.getQueryParameter("data")
                    }
                }
            }
        }
    }
}

