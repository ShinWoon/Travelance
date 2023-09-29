package com.moneyminions.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.viewmodel.MainViewModel

private const val TAG = "MainScreen_D210"
//var startDestination: String = Screen.Home.route //나중에 viewModel로 빼야함
//var startDestination: String = Screen.Login.route //나중에 viewModel로 빼야함

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    Log.d(TAG, "MainScreen: ${mainViewModel.getJwtToken()}")
    if(mainViewModel.getJwtToken().role == "MEMBER"){
//        mainViewModel.putTravelingRoomId(1)
        mainViewModel.setSelectRoomId(mainViewModel.getTravelingRoomId()) // 진행 중인 여행방을 selectRoom에 저장
        navController.navigate(Screen.Home.route)
    }else{
        navController.navigate(Screen.Login.route)
    }
    
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen(navController = rememberNavController())
}
