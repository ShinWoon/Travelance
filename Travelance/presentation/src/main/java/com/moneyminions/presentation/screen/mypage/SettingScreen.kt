package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.SettingItem

private const val TAG = "SettingScreen D210"
@Composable
fun SettingScreen(
    navController: NavHostController
){
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        TopBar(
            navController = navController,
            title = "설정"
        )
        Spacer(modifier = Modifier.size(16.dp))
        SettingItem(icon = painterResource(id = R.drawable.ic_setting), text = "회원 정보 수정") {
            //회원 정보 수정으로 이동
            Log.d(TAG, "회원 정보 수정 클릭...")
            navController.navigate(Screen.EditUser.route)
        }
        SettingItem(icon = painterResource(id = R.drawable.ic_setting), text = "로그아웃") {
            //로그 아웃 로직
        }
        SettingItem(icon = painterResource(id = R.drawable.ic_setting), text = "회원탈퇴") {
            //회원 가입 로직
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SettingScreenPreview(){
    SettingScreen(navController = rememberNavController())
}