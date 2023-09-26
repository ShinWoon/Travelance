package com.moneyminions.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.moneyminions.presentation.navigation.BottomNavItem
import com.moneyminions.presentation.navigation.NavGraph
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.MainScreen
import com.moneyminions.presentation.theme.MyApplicationTheme
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity D210"

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isAuthenticated = remember { mutableStateOf(false) }
            val context = LocalContext.current

            val navController = rememberAnimatedNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            MyApplicationTheme {
                Scaffold(
                    bottomBar = {
                        if (currentRoute == null || isBottomNavItem(currentRoute!!)) {
                            MainBottomNavigationBar(navController = navController)
                        }
                    },
                    containerColor = White,
                ) {
                    NavGraph(
                        innerPaddings = it,
                        startDestination = Screen.Main.route,
                        navController = navController,
                    )
                }


//                val systemUiController = rememberSystemUiController()
//                SideEffect {
//                    systemUiController.setStatusBarColor(
//                        color = White,
//                        darkIcons = true,
//                    )
//                }
//                SideEffect {
//                    systemUiController.setNavigationBarColor(
//                        color = White,
//                        darkIcons = true,
//                    )
//                }
//                MainScreen(rememberAnimatedNavController())

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

fun isBottomNavItem(route: String): Boolean {
    return when (route) {
        BottomNavItem.Home.route, BottomNavItem.TravelList.route, BottomNavItem.MyPage.route -> true
        else -> false
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val bottomNavItems = listOf(
        BottomNavItem.TravelList,
        BottomNavItem.Home,
        BottomNavItem.MyPage,
    )
    NavigationBar(
        tonalElevation = 0.dp,
        modifier = Modifier.graphicsLayer {
            clip = true
            shadowElevation = 20f
        }
    ) {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            val current = backStackEntry.value?.destination?.route

            Log.d(TAG, "MainBottomNavigationBar: $selected / $current")

            NavigationBarItem(
                selected = selected,
                // 해당 아이템의 route를 설정
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(current!!) {
                            inclusive = true
                        }
                    }
                },
                label = {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PinkDarkest,
                    selectedTextColor = PinkDarkest,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray,
                    indicatorColor = White
                ),
            )
        }
    }
}


