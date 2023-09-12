package com.moneyminions.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneyminions.presentation.screen.announcement.AnnouncementScreen
import com.moneyminions.presentation.screen.example.ExampleScreen
import com.moneyminions.presentation.screen.home.HomeScreen
import com.moneyminions.presentation.screen.mypage.MyPageScreeen
import com.moneyminions.presentation.screen.mypage.SettingScreen
import com.moneyminions.presentation.screen.travellist.CreateTravelScreen
import com.moneyminions.presentation.screen.travellist.TravelListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = Screen.Example.route
        ){
            ExampleScreen(navController = navController)
        }
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.TravelList.route
        ){
            TravelListScreen(navController = navController)
        }
        composable(
            route = Screen.CreateTravel.route
        ){
            CreateTravelScreen(navController = navController)
        }
        composable(
            route = Screen.MyPage.route
        ){
            MyPageScreeen(navController = navController)
        }
        composable(
            route = Screen.Setting.route
        ){
            SettingScreen(navController = navController)
        }
        composable(
            route = Screen.Announcement.route
        ){
            AnnouncementScreen(navController = navController)
        }
    }
} // End of setUpNavGraph