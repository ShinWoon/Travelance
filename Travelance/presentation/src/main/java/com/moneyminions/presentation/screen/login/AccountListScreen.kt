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
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.AccountDto

val accountList = listOf(
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    ),
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    ),
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    )
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
                items(accountList){
                    var isSelectedState by remember { mutableStateOf(it.isSelected) }
                    Log.d(TAG, "AccountListScreen: $isSelectedState")
                    AccountRowItem(
                        logo = it.logo,
                        name = it.name,
                        number = it.number,
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