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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moneyminions.presentation.screen.home.view.BottomCardContainer
import com.moneyminions.presentation.screen.home.view.FriendComponent
import com.moneyminions.presentation.screen.home.view.GraphPage
import com.moneyminions.presentation.screen.home.view.TopComponent
import com.moneyminions.presentation.screen.home.view.TravelReadyComponent
import com.moneyminions.presentation.screen.home.view.UseMoneyPage
import com.moneyminions.presentation.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Home(navController)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
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
        TopComponent(
            homeViewModel = homeViewModel,
            navController = navController,
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        if(homeViewModel.isTravelStart.value) { // 여행 시작
            TravelStartPager(cardHeight)
        } else { // 사전 정산 중
            TravelReadyPager(
                homeViewModel,
                cardHeight
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        FriendComponent(homeViewModel)
        
        Spacer(modifier = Modifier.height(16.dp))
        BottomCardContainer(navController)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TravelReadyPager(
    homeViewModel: HomeViewModel,
    cardHeight: Dp
) {
    val pagerState = rememberPagerState()
    
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        count = 4,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                TravelReadyComponent(
                    homeViewModel = homeViewModel,
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    totalDot = 4
                )
            }
            1 -> {
                GraphPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    totalDot = 4
                )
            }
            2 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "전체 내역",
                    money = 24000,
                    totalDot = 4
                )
            }
            3 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "나의 전체 내역",
                    money = 24000,
                    totalDot = 4
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TravelStartPager(
    cardHeight: Dp
) {
    val pagerState = rememberPagerState()
    
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        count = 3,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                GraphPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    totalDot = 3
                )
            }
            1 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "전체 내역",
                    money = 24000,
                    totalDot = 3
                )
            }
            2 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "나의 전체 내역",
                    money = 24000,
                    totalDot = 3
                )
            }
        }
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