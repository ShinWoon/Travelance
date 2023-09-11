package com.moneyminions.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moneyminions.presentation.screen.home.view.BottomCardContainer
import com.moneyminions.presentation.screen.home.view.FriendComponent
import com.moneyminions.presentation.screen.home.view.GraphPage
import com.moneyminions.presentation.screen.home.view.TopComponent
import com.moneyminions.presentation.screen.home.view.UseMoneyPage

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Home()
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home() {
    // Pager State
    val pagerState = rememberPagerState()
    val scrollableState = rememberScrollState()
    
    // Main Card Height
    val cardHeight = 440.dp
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollableState)
            .padding(16.dp, 16.dp, 16.dp, 16.dp),
    ) {
        
        TopComponent()

        Spacer(modifier = Modifier.height(8.dp))

        // Pager
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            count = 3,
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> GraphPage(pagerState, cardHeight)
                1 -> UseMoneyPage(
                    pagerState,
                    cardHeight,
                    title = "전체 내역",
                    money = 24000
                )
                2 -> UseMoneyPage(
                    pagerState,
                    cardHeight,
                    title = "나의 전체 내역",
                    money = 24000
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FriendComponent()

        Spacer(modifier = Modifier.height(16.dp))

        BottomCardContainer()
    }
}

// dot indicator
@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),

    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor),
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor),
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
