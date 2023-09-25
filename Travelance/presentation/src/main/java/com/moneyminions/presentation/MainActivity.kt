package com.moneyminions.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.moneyminions.presentation.screen.MainScreen
import com.moneyminions.presentation.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"
@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isAuthenticated = remember { mutableStateOf(false) }
            val context = LocalContext.current
            MyApplicationTheme {
                MainScreen()

                // 카카오
//                var keyHash = Utility.getKeyHash(this)
//                Log.d(TAG, "Kakao HashKey : $keyHash")
    
    
                /**
                 * 지문 인증 실행 부분에서 구현하면 됨 (mainActivity는 FragmentActivity()로 상속되게 함)
                 * 이때 mainActivity에 this@MainActivity로 불러오고
                 */
//                val con = BiometricUtils.status(LocalContext.current)
//                if (con && !isAuthenticated.value) {
//                    BiometricUtils.authenticate(
//                        this@MainActivity,
//                        title = "Authentication Required",
//                        negativeText = "Password",
//                        onSuccess = {
//                            runOnUiThread {
//                                Log.d("success!!", "onCreate: {$it}")
//                                Toast.makeText(
//                                    context,
//                                    "Authenticated successfully $it",
//                                    Toast.LENGTH_SHORT,
//                                )
//                                    .show()
//                                isAuthenticated.value = true
//                            }
//                        },
//                        onError = { _, _ ->
//                            /*
//                            On Clicking on Password
//                             */
//                        },
//                        onFailed = {
//                            runOnUiThread {
//                                Toast.makeText(
//                                    context,
//                                    "Authentication failed",
//                                    Toast.LENGTH_SHORT,
//                                )
//                                    .show()
//                                Log.d("Fail ->", "")
//                            }
//                        },
//                    )
//                }
                
                
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


