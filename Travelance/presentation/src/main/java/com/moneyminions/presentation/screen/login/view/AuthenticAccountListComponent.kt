package com.moneyminions.presentation.screen.login.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.mypage.view.AccountColumnItem
import com.moneyminions.presentation.utils.Constants
import com.moneyminions.presentation.viewmodel.login.AccountAuthenticationViewModel

private const val TAG = "AuthenticAccountListCom D210"
@Composable
fun AuthenticAccountListComponent(
    accountAuthenticationViewModel: AccountAuthenticationViewModel = hiltViewModel()
){

    // 선택된 계좌의 인덱스를 저장하는 상태
    var selectedAccountIndex by remember {
        mutableIntStateOf(-1) // 초기에는 선택된 항목이 없으므로 -1로 초기화
    }
    LaunchedEffect(selectedAccountIndex) {
        Log.d(TAG, "selectedIdx : $selectedAccountIndex")
        if(selectedAccountIndex == -1) {
            accountAuthenticationViewModel.setBankName("")
        }else{
            accountAuthenticationViewModel.setBankName(Constants.AUTHENTICATION_ACCOUNT_LIST[selectedAccountIndex].bankName)
        }
    }

    Text(
        text = "계좌 목록",
        style = CustomTextStyle.pretendardBold16
    )
    Spacer(modifier = Modifier.size(16.dp))
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(Constants.AUTHENTICATION_ACCOUNT_LIST) { it ->
            Log.d(TAG, "current : ${Constants.AUTHENTICATION_ACCOUNT_LIST.indexOf(it)}")
            AccountColumnItem(
                logo = Constants.ACCOUNT_LOGO_LIST[it.idx!!],
                compoany = it.bankName,
                isSelected = selectedAccountIndex == Constants.AUTHENTICATION_ACCOUNT_LIST.indexOf(it)
            ) {
                Constants.AUTHENTICATION_ACCOUNT_LIST.forEach {
                    it.isSelected = false
                }
                if (selectedAccountIndex == Constants.AUTHENTICATION_ACCOUNT_LIST.indexOf(it)) {
                    selectedAccountIndex = -1
                } else {
                    it.isSelected = true
                    selectedAccountIndex =
                        Constants.AUTHENTICATION_ACCOUNT_LIST.indexOf(it)
                }
            }
        }
    }
}