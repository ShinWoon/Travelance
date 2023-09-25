package com.moneyminions.presentation.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.moneyminions.presentation.navigation.BottomNavItem
import com.moneyminions.presentation.navigation.NavGraph
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.viewmodel.MainViewModel

import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White

private const val TAG = "MainScreen_D210"
//var startDestination: String = Screen.Home.route //나중에 viewModel로 빼야함
//var startDestination: String = Screen.Login.route //나중에 viewModel로 빼야함

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
//    navController: NavHostController = rememberAnimatedNavController(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var startDestination = ""

    Scaffold(
        bottomBar = {
            if (currentRoute == null || isBottomNavItem(currentRoute!!)) {
                MainBottomNavigationBar(navController = navController)
            }
        },
        containerColor = White,
    ) {
//        Surface(
//            modifier = Modifier.padding(it),
//        ) {
//            NavGraph(navController = navController, startDestination = startDestination)
//        }
        if(mainViewModel.getRole() == "MEMBER"){
            startDestination = Screen.Home.route
        }else{
            startDestination = Screen.Login.route
        }
        NavGraph(
            innerPaddings = it,
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
<<<<<<< Travelance/presentation/src/main/java/com/moneyminions/presentation/screen/MainScreen.kt
=======
//                    startDestination = item.route
>>>>>>> Travelance/presentation/src/main/java/com/moneyminions/presentation/screen/MainScreen.kt
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen(navController = rememberNavController())
}
