package com.moneyminions.presentation.screen.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.mypage.AccountDto
import com.moneyminions.presentation.screen.mypage.view.AccountColumnItem

@Composable
fun AuthenticAccountListComponent(

){
    // 선택된 계좌의 인덱스를 저장하는 상태
    var selectedAccountIndex by remember {
        mutableIntStateOf(-1) // 초기에는 선택된 항목이 없으므로 -1로 초기화
    }
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
        items(accountList) { it ->
            AccountColumnItem(
                logo = it.logo,
                compoany = it.name,
                isSelected = selectedAccountIndex == accountList.indexOf(it)
            ) {
                if (selectedAccountIndex == accountList.indexOf(it)) {
                    accountList.forEach {
                        it.isSelected = false
                    }
                    selectedAccountIndex = -1
                } else {
                    accountList.forEach {
                        it.isSelected = false
                    }
                    it.isSelected = true
                    selectedAccountIndex =
                        accountList.indexOf(it)
                }
            }
        }
    }
}