package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.utils.Constants

val accountList = listOf<AccountDto>(
)

@Composable
fun AccountListComponent(
    accountList: List<AccountDto>,
    onDelete: (String, String) -> Unit
){
    Text(
        text = "계좌 목록",
        style = CustomTextStyle.pretendardBold16
    )
    Spacer(modifier = Modifier.size(8.dp))
    Divider(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(GraphGray)
        .padding(horizontal = 16.dp)
    )
    LazyColumn{
        items(accountList){
            AccountRowItem(
                logo = Constants.ACCOUNT_LOGO_LIST[it.idx!!],
                name = it.bankName,
                number = it.accountNumber!!,
                type = "delete",
                onDeleted = {onDelete(it.bankName, it.accountNumber!!)}
            )
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
}