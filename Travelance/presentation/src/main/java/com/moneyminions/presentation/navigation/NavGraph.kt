package com.moneyminions.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.moneyminions.presentation.screen.announcement.AnnouncementScreen
import com.moneyminions.presentation.screen.detail.DetailScreen
import com.moneyminions.presentation.screen.example.ExampleScreen
import com.moneyminions.presentation.screen.game.BottleGameScreen
import com.moneyminions.presentation.screen.game.CardGameScreen
import com.moneyminions.presentation.screen.game.GameListScreen
import com.moneyminions.presentation.screen.game.TeamBuildingGameScreen
import com.moneyminions.presentation.screen.game.WordGameScreen
import com.moneyminions.presentation.screen.home.HomeScreen
import com.moneyminions.presentation.screen.login.AccountAuthenticationScreen
import com.moneyminions.presentation.screen.login.AccountListScreen
import com.moneyminions.presentation.screen.login.CardListScreen
import com.moneyminions.presentation.screen.login.LoginScreen
import com.moneyminions.presentation.screen.login.NicknamePasswordScreen
import com.moneyminions.presentation.screen.mypage.EditUserScreen
import com.moneyminions.presentation.screen.mypage.MyPageScreeen
import com.moneyminions.presentation.screen.mypage.SettingScreen
import com.moneyminions.presentation.screen.traveldone.TravelDoneScreen
import com.moneyminions.presentation.screen.travellist.CreateTravelScreen
import com.moneyminions.presentation.screen.travellist.TravelListScreen
import com.moneyminions.presentation.screen.travelmap.TravelMapScreen
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.login.LoginViewModel

private const val TAG = "NavGraph_D210"
@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    innerPaddings: PaddingValues,
    navController: NavHostController,
    startDestination: String,
    loginViewModel: LoginViewModel = hiltViewModel() ,
    mainViewModel: MainViewModel,
) {
    AnimatedNavHost(
        modifier = Modifier.padding(innerPaddings),
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(tween(500)) },
//        exitTransition = { fadeOut(tween(300)) },
        popEnterTransition = { fadeIn(tween(500)) },
//        popExitTransition = { fadeOut(tween(500)) },
//        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
//        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(
            route = Screen.Example.route,
        ) {
            ExampleScreen(navController = navController)
        }
//        composable(
//            route = Screen.Main.route
//        ) {
//            MainScreen(navController = navController, mainViewModel = mainViewModel)
//        }
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.TravelList.route,
        ) {
            TravelListScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.CreateTravel.route,
        ) {
            CreateTravelScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.MyPage.route,
        ) {
            MyPageScreeen(navController = navController)
        }
        composable(
            route = Screen.Setting.route,
        ) {
            SettingScreen(navController = navController)
        }
        composable(
            route = Screen.Announcement.route,
        ) {
            AnnouncementScreen(navController = navController)
        }
        composable(
            route = Screen.EditUser.route,
        ) {
            EditUserScreen(navController = navController)
        }
        composable(
            route = Screen.AccountAuthentication.route,
        ) {
            AccountAuthenticationScreen(navController = navController)
        }
        composable(
            route = Screen.AccountList.route,
        ) {
            AccountListScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(
            route = Screen.CardList.route,
        ) {
            CardListScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(
            route = Screen.TravelMap.route,
        ) {
            TravelMapScreen(navController = navController)
        }
        composable(
            route = "${Screen.GameList.route}/{travelId}",
        ) {
            val travelId = it.arguments?.getString("travelId")?.toInt()
            if (travelId != null) {
                GameListScreen(navController = navController, travelId = travelId)
            }
        }
        composable(
            route = "${Screen.CardGame.route}/{travelId}",
        ) {
            val travelId = it.arguments?.getString("travelId")?.toInt()
            if (travelId != null) {
                CardGameScreen(navController = navController, travelId = travelId)
            }
        }
        composable(
            route = Screen.BottleGame.route,
        ) {
            BottleGameScreen(navController = navController)
        }
        composable(
            route = "${Screen.TeamBuildingGame.route}/{travelId}",
        ) {
            val travelId = it.arguments?.getString("travelId")?.toInt()
            if (travelId != null) {
                TeamBuildingGameScreen(navController = navController, travelId = travelId)
            }
        }
        composable(
            route = Screen.WordGame.route,
        ) {
            WordGameScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route,
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.SubHome.route,
        ) {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
//        composable(
//            route = "Screen.SubHome.route/{roomId}",
//            arguments = listOf(navArgument("roomId") { type = NavType.IntType })
//        ) {backStackEntry ->
//            val roomId = backStackEntry.arguments?.getInt("roomId")
//            if (roomId != null) {
//                HomeScreen(navController = navController, roomId = roomId)
//            }
//        }
        composable(
            route = "${Screen.TravelDetail.route}/{travelId}",
        ) {
            val travelId = it.arguments?.getString("travelId")?.toInt()
            Log.d(TAG, "NavGraph: travelId $travelId")
            if (travelId != null) {
                DetailScreen(navController = navController, travelId = travelId, mainViewModel = mainViewModel)
            }
        }
        composable(
            route = Screen.NicknamePassword.route,
        ) {
            NicknamePasswordScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(
            route = "${Screen.TravelDone.route}/{travelName}",
        ) {
            val travelName = it.arguments?.getString("travelName")
            if (travelName != null) {
                TravelDoneScreen(navController = navController, travelName = travelName)
            }
        }
        composable(
            route = "${Screen.TravelEdit.route}/{roomId}",
        ) {
            val roomId = it.arguments?.getString("roomId")?.toInt()
            if (roomId != null) {
                CreateTravelScreen(navController = navController, mainViewModel = mainViewModel, roomId = roomId)
            }
        }
    }
} // End of setUpNavGraph
