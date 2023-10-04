package com.moneyminions.presentation

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.MainScreen
import com.moneyminions.presentation.screen.result.SettleResultReceiveScreen
import com.moneyminions.presentation.theme.MyApplicationTheme
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.Constants.CHANNEL_ID
import com.moneyminions.presentation.utils.Constants.CHANNEL_NAME
import com.moneyminions.presentation.utils.createNotificationChannel
import com.moneyminions.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(notificationManager, CHANNEL_ID, CHANNEL_NAME)
        setContent {
            val intent: Intent = intent

            var isAuthenticated = remember { mutableStateOf(false) }

            val navController = rememberAnimatedNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            MyApplicationTheme {
                val mainViewModel: MainViewModel = hiltViewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White,
                ) {
                    // Intent로부터 데이터 추출
                    val type = intent.getStringExtra("type")
                    if (type != null) {
                        Log.d(TAG, "onCreate type: $type")
                        Log.d(TAG, "onCreate roomId : ${intent.getIntExtra("roomId",0)}")
//                        SettleResultReceiveScreen(navController = rememberAnimatedNavController(), roomId = intent.getIntExtra("roomId",0))
                        val startDestination = Screen.SettleResult.route
                        Log.d(TAG, "onCreate startDestination $startDestination")
                        MainScreen(
                            startDestination = startDestination,
                            mainViewModel = mainViewModel,
                            context = applicationContext,
                            roomId = intent.getIntExtra("roomId",0)
                        )
                    }
                    else {
                        val startDestination =
                            if (mainViewModel.getJwtToken().role == "MEMBER") {
                                Log.d(
                                    TAG,
                                    "MainScreen 진행 중인 room ID: ${mainViewModel.getTravelingRoomId()} ",
                                )
                                mainViewModel.setSelectRoomId(mainViewModel.getTravelingRoomId()) // 진행 중인 여행방을 selectRoom에 저장

                                /**
                                 * 카카오 공유 API 반환 값 수신
                                 */
                                if (Intent.ACTION_VIEW == intent.action) {
                                    val uri = intent.data
                                    if (uri != null) {
                                        Log.d(
                                            TAG,
                                            "onCreate: 카카오 공유 ${uri.getQueryParameter("roomId")} \n ${uri.getQueryParameter("route")} \n ${uri.getQueryParameter("data")}",
                                        )
//                                    uri.getQueryParameter("roomId")
//                                    uri.getQueryParameter("route")
//                                    uri.getQueryParameter("data")
                                        uri.getQueryParameter("roomId")?.let { mainViewModel.setInviteRoomId(it.toInt()) }
                                        Screen.TravelList.route
                                    } else {
                                        Screen.Home.route
                                    }
                                } else {
                                    Screen.Home.route
                                }
                            } else {
                                Screen.Login.route
                            }

                        Log.d(TAG, "JWTTOKEN: ${mainViewModel.getJwtToken().accessToken}")
                        Log.d(TAG, "startDestination: $startDestination")
                        MainScreen(
                            startDestination = startDestination,
                            mainViewModel = mainViewModel,
                            context = applicationContext,
                        )
                    }
                }
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = White,
                        darkIcons = true,
                    )
                }
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = White,
                        darkIcons = true,
                    )
                }

                /**
                 * 카카오 공유 API 반환 값 수신
                 */
//                if (Intent.ACTION_VIEW == intent.action) {
//                    val uri = intent.data
//                    if (uri != null) {
//                        Log.d(
//                            TAG,
//                            "onCreate: 카카오 공유 ${uri.getQueryParameter("roomId")} \n ${uri.getQueryParameter("route")} \n ${uri.getQueryParameter("data")}"
//                        )
//                        uri.getQueryParameter("roomId")
//                        uri.getQueryParameter("route")
//                        uri.getQueryParameter("data")
//                    }
//                }

//                MainScreen(rememberAnimatedNavController())

// //                 카카오
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
//                            Log.d(TAG, "onCreate: 지문인증 성공")
//                            isAuthenticated.value = true
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
//                                Log.d(TAG, "지문 인증 실패")
//                            }
//                            Log.d(TAG, "지문 인증 실패")
//                        },
//                    )
//                }
            }
        }
    }
}
