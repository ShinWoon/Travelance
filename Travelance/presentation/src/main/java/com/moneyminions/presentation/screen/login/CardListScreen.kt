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
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.mypage.CardDto
import com.moneyminions.presentation.screen.mypage.accountList

private const val TAG = "CardListScreen D210"
@Composable
fun CardListScreen(
    navController: NavHostController
){
    val cardList = listOf(
        CardDto(
            name = "카드 별칭",
            number = "123456789012",
            idx = 0,
            company = "카드사명",
            logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
        ),
        CardDto(
            name = "카드 별칭",
            number = "123456789012",
            idx = 1,
            company = "카드사명",
            logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
        ),
        CardDto(
            name = "카드 별칭",
            number = "123456789012",
            idx = 2,
            company = "카드사명",
            logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "카드 목록"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn{
                items(cardList){
                    var isSelectedState by remember { mutableStateOf(it.isSelected) }
                    Log.d(TAG, "AccountListScreen: $isSelectedState")
                    CardRowItem(
                        name = it.name,
                        number = it.number,
                        idx = it.idx,
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

            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CardListScreenPreview(){
    CardListScreen(navController = rememberNavController())
}