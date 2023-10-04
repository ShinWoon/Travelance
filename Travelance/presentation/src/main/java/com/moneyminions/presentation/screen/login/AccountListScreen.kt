package com.moneyminions.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.accountList
import com.moneyminions.presentation.utils.Constants
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.login.AccountListViewModel
import com.moneyminions.presentation.viewmodel.login.LoginViewModel
import com.moneyminions.presentation.viewmodel.mypage.EditUserViewModel
import kotlinx.coroutines.launch

private const val TAG = "AccountListScreen D210"

@Composable
fun AccountListScreen(
    navController: NavHostController,
    accountListViewModel: AccountListViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel,
    editUserViewModel: EditUserViewModel
){
    accountListViewModel.setExistingAccountList(editUserViewModel)

    val coroutineScope = rememberCoroutineScope()
    val accountListResultState by accountListViewModel.accountListResult.collectAsState() //계좌 불러오는 api 결과 상태
    val accountListState =
        accountListViewModel.accoutList.collectAsState() //내가 이 화면에 사용할 내 계좌 리스트의 상태
    NetworkResultHandler(
        state = accountListResultState,
        errorAction = {
            Log.d(TAG, "AccountList error...")
        },
        successAction = {
            coroutineScope.launch {
                Log.d(TAG, "AccountList success : $it ")
                accountListViewModel.setAccountList(it)
            }
        }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            accountListViewModel.getAccountList()
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn {
            items(accountListState.value) {
                var isSelectedState by remember { mutableStateOf(it.isSelected) }
                Log.d(TAG, "AccountListScreen: $isSelectedState")
                AccountRowItem(
                    logo = Constants.ACCOUNT_LOGO_LIST[it.idx!!],
                    name = it.bankName,
                    number = it.accountNumber!!,
                    type = "select",
                    isSelected = isSelectedState,
                    onSelected = {
                        isSelectedState = !isSelectedState!!
                        it.isSelected = !it.isSelected!!
                        Log.d(TAG, "AccountListScreen: $accountList")
                    }
                )
            }
        }
        MinionPrimaryButton(
            content = "다음",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //닉네임, 비밀번호 설정 화면으로 이동
            loginViewModel.setMemberAccountList(accountListState.value.filter { it.isSelected!! })
            Log.d(
                TAG,
                "AccountList mainviewmodel의 memberInfo : ${loginViewModel.memberInfo}"
            )
            navController.navigate(Screen.CardList.route)
        }
    }

}