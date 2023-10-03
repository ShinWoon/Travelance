package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.presentation.common.CardFrame
import com.moneyminions.presentation.common.CustomTextStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageCardListComponent(
    pagerState: PagerState,
    cardList: List<CardDto>
){
    Text(
        text = "카드 목록",
        style = CustomTextStyle.pretendardSemiBold16,
    )
    Spacer(modifier = Modifier.size(16.dp))
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(
            LocalConfiguration.current.screenWidthDp.dp * 0.7f, // 총 화면의 70프로를 고정
        ),
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 44.dp), // 가운데 정렬
    ) { idx ->
        val card = cardList[idx]
        Card() {
            // Card content
            CardFrame(
                name = card.name,
                number = card.number,
                idx = card.idx!!
            )
        }
    }
}