package com.moneyminions.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold10
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.BottomNavItem
import com.moneyminions.presentation.navigation.NavGraph
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.theme.pretendard
import com.moneyminions.presentation.viewmodel.MainViewModel

private const val TAG = "MainScreen_D210"
//var startDestination: String = Screen.Home.route //나중에 viewModel로 빼야함
//var startDestination: String = Screen.Login.route //나중에 viewModel로 빼야함

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    startDestination: String,
    navController: NavHostController = rememberAnimatedNavController(),
    mainViewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            if (currentRoute != null && !Screen.checkToolBar(currentRoute!!)) {
                TopBar(
                    navController = navController,
                    currentRoute = currentRoute,
                )
            }
        },
        bottomBar = {
            if (currentRoute == null || isBottomNavItem(currentRoute!!)) {
                MainBottomNavigationBar(navController = navController)
            }
        },
        containerColor = White,
    ) {
        NavGraph(
            innerPaddings = it,
            startDestination = startDestination,
            navController = navController,
        )
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
                        style = pretendardSemiBold10,
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
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen(navController = rememberNavController())
}
