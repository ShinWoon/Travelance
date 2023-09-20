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
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.mypage.CardDto
import com.moneyminions.presentation.theme.GraphGray

val cardList = listOf(
    CardDto(
        name = "카드 별칭 1",
        number = "123456789012",
        idx = 0,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    ),
    CardDto(
        name = "카드 별칭 2",
        number = "123456789012",
        idx = 1,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    ),
    CardDto(
        name = "카드 별칭 3",
        number = "123456789012",
        idx = 2,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    )
)

@Composable
fun CardListComponent(

){
    Text(
        text = "카드 목록",
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
        items(cardList){
            CardRowItem(
                name = it.name,
                number = it.number,
                idx = it.idx,
                type = "delete",
                onDeleted = {
                    //삭제 로직
                }
            )
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    PlusButtonWithText(text = "카드 추가하기") {
        //카드 추가하기 화면으로 이동
    }
}