package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.SettingItem
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.WithDrawRed
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.mypage.SettingViewModel

private const val TAG = "SettingScreen D210"
@Composable
fun SettingScreen(
    navController: NavHostController,
    settingViewModel: SettingViewModel = hiltViewModel()
){
    val logoutResultState by settingViewModel.logoutResult.collectAsState()
    NetworkResultHandler(
        state = logoutResultState,
        errorAction = {
            Log.d(TAG, "로그아웃 오류")
        },
        successAction = {
            navController.navigate(Screen.Login.route){
                popUpTo(Screen.Home.route){ inclusive = true }
            }
        }
    )

    val joinOutResultState by settingViewModel.joinOutResult.collectAsState()
    NetworkResultHandler(
        state = joinOutResultState,
        errorAction = {
            Log.d(TAG, "회원탈퇴 오류")
        },
        successAction = {
            navController.navigate(Screen.Login.route){
                popUpTo(Screen.Home.route){ inclusive = true }
            }
        }
    )

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.size(16.dp))
        SettingItem(icon = painterResource(id = R.drawable.ic_edit), color = DarkerGray, text = "회원 정보 수정") {
            //회원 정보 수정으로 이동
            Log.d(TAG, "회원 정보 수정 클릭...")
            navController.navigate(Screen.EditUser.route)
        }
        SettingItem(icon = painterResource(id = R.drawable.ic_logout), color = DarkerGray, text = "로그아웃") {
            //로그 아웃 로직
            settingViewModel.logout()
        }
        SettingItem(icon = painterResource(id = R.drawable.ic_withdraw), color = WithDrawRed, text = "회원탈퇴") {
            //회원 탈퇴 로직
            settingViewModel.joinOut()
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SettingScreenPreview(){
    SettingScreen(navController = rememberNavController())
}