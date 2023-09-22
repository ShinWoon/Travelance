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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.accountList
import com.moneyminions.presentation.utils.Constants

val accountList = listOf<AccountDto>(
)

private const val TAG = "AccountListScreen D210"
@Composable
fun AccountListScreen(
    navController: NavHostController
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "계좌 목록"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn{
                items(Constants.AUTHENTICATION_ACCOUNT_LIST){
                    var isSelectedState by remember { mutableStateOf(it.isSelected) }
                    Log.d(TAG, "AccountListScreen: $isSelectedState")
                    AccountRowItem(
                        logo = Constants.ACCOUNT_LOGO_LIST[it.idx],
                        name = it.name,
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
                navController.navigate(Screen.CardList.route)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountListScreenPreview(){
    AccountListScreen(navController = rememberNavController())
}