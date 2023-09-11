package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.screen.home.DotsIndicator
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UseMoneyPage(
    pagerState: PagerState,
    cardHeight: Dp,
    title: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = title, style = CustomTextStyle.pretendardBold16)
                Text(text = "전체 보기", style = CustomTextStyle.pretendardSemiBold12)
            }

            LazyColumn(
                modifier = Modifier
                    .weight(9.5f)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(20) {
                    UseMoneyItem()
                }
            }
            
            
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                DotsIndicator(
                    totalDots = 3,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = PinkDarkest,
                    unSelectedColor = PinkLightest,
                )
            }
        } 
    }
}

@Composable
fun UseMoneyItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        Row (
            modifier = Modifier.weight(2f)
        ){
            MinionProfile()
    
            Spacer(modifier = Modifier.width(8.dp))
    
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "지출 내역!!",
                    style = CustomTextStyle.pretendardBold16,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "10 Oct 21",
                    style = CustomTextStyle.pretendardLight12,
                )
            }
        }
    
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = "\u20A9 24,000" ,
                style = CustomTextStyle.pretendardBold16,
            )
        }
    }
}
