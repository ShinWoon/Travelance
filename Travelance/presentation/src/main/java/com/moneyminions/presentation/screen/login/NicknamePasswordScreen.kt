package com.moneyminions.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.login.NicknamePasswordViewModel

private const val TAG = "NicknamePasswordScreen D210"
@Composable
fun NicknamePasswordScreen(
    navController: NavHostController,
    nicknamePasswordViewModel: NicknamePasswordViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "프로필 설정"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "닉네임",
                    hint = "닉네임을 입력해주세요",
                    value = nicknamePasswordViewModel.nickname.value,
                    onValueChange = {nicknamePasswordViewModel.setNickname(it)}
                )
                TextFieldWithTitle(
                    title = "비밀번호",
                    hint = "비밀번호를 입력해주세요",
                    value = nicknamePasswordViewModel.password.value,
                    onValueChange = {nicknamePasswordViewModel.setPassword(it)}
                )
                TextFieldWithTitle(
                    title = "비밀번호 확인",
                    hint = "비밀번호를 입력해주세요",
                    value = "",
                    onValueChange = {}
                )
            }
            MinionPrimaryButton(
                content = "완료",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                //TODO VAILD CHECK 해야 함
                mainViewModel.setNickname(nicknamePasswordViewModel.nickname.value)
                mainViewModel.setPassword(nicknamePasswordViewModel.password.value)
                Log.d(TAG, "최종 memberInfo : ${mainViewModel.memberInfo}")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun NicknamePasswordScreenPreview(){
    NicknamePasswordScreen(navController = rememberNavController())
}