package com.moneyminions.presentation.screen.home

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.moneyminions.domain.model.home.TravelRoomInfoDto
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.home.view.BottomCardContainer
import com.moneyminions.presentation.screen.home.view.FriendComponent
import com.moneyminions.presentation.screen.home.view.GraphPage
import com.moneyminions.presentation.screen.home.view.TopComponent
import com.moneyminions.presentation.screen.home.view.TravelReadyComponent
import com.moneyminions.presentation.screen.home.view.UseMoneyPage
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.home.HomeViewModel

private const val TAG = "HomeScreen_D210"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    
//    Log.d(TAG, "HomeScreen: on")
    // 여행방 시작
    val startTravelState by homeViewModel.startTravelResult.collectAsState()
    NetworkResultHandler(
        state = startTravelState,
        errorAction = {
            Log.d(TAG, "HomeScreen: 아직 진행 중인 여행이 있음.")
        },
        successAction = {
            if(it.result.toInt() != 0) {
                mainViewModel.putTravelingRoomId(it.result.toInt())
                navController.navigate(Screen.Home.route)
                Log.d(TAG, "HomeScreen 여행방 시작 성공: $it")
            } else {
                Log.d(TAG, "HomeScreen: 이미 진행중인 여행이 있음")
            }
        }
    )
//    Log.d(TAG, "HomeScreen: viewModel -> ${homeViewModel.hashCode()}")
//    Log.d(TAG, "HomeScreen default: ${homeViewModel.travelRoomInfo.hashCode()}")
    
    // 여행방 정보 GET
    val getTravelRoomInfoState by homeViewModel.getTravelRoomInfoResult.collectAsState()
    NetworkResultHandler(
        state = getTravelRoomInfoState,
        errorAction = {
            Log.d(TAG, "HomeScreen: 방 정보 얻기 실패")
        },
        successAction = {
            Log.d(TAG, "HomeScreen 방 정보 얻기 성공 $it")
            homeViewModel.refreshRoomInfo(it)
        }
    )

    // 여행방 친구 GET
    val getTravelRoomFriendState by homeViewModel.getTravelRoomFriendResult.collectAsState()
    NetworkResultHandler(
        state = getTravelRoomFriendState,
        errorAction = {
            Log.d(TAG, "HomeScreen: 친구 목록 얻기 실패")
        },
        successAction = {
            Log.d(TAG, "HomeScreen 친구 목록 얻기 성공: $it")
            homeViewModel.refreshRoomFriendInfo(it.toMutableList())
        }
    )
    
    
    Log.d(TAG, "selectRoomId: ${mainViewModel.selectRoomId.value} / ${mainViewModel.getTravelingRoomId()}")
    if (mainViewModel.selectRoomId.value == 0 && mainViewModel.getTravelingRoomId() == 0) { // 진행 중인 방이 없다면.
        Log.d(TAG, "HomeScreen: NoRoomScreen on")
        NoRoomScreen()
    } else {
        // 홈 정보 GET
        LaunchedEffect(Unit) {
            homeViewModel.apply {
                getTravelRoomInfo(mainViewModel.selectRoomId.value)
                getTravelRoomFriend(mainViewModel.selectRoomId.value)
            }
        }
//        homeViewModel.getTravelRoomInfo(mainViewModel.selectRoomId.value)
        Home(
            navController = navController,
            mainViewModel = mainViewModel,
            homeViewModel = homeViewModel,
            travelInfo = homeViewModel.travelRoomInfo.value
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Home(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    travelInfo: TravelRoomInfoDto
) {
    
//    Log.d(TAG, "Home: on")
    var scrollableState = rememberScrollState()
    
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
        
        when (travelInfo.isDone) {
            "BEFORE" -> TravelReadyPager(
                cardHeight = cardHeight,
                mainViewModel = mainViewModel,
                homeViewModel = homeViewModel,
                travelInfo = travelInfo,
            )
            "NOW", "WAIT" -> TravelStartPager(
                cardHeight = cardHeight,
                travelInfo = travelInfo,
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        FriendComponent(homeViewModel = homeViewModel)
        
        Spacer(modifier = Modifier.height(16.dp))
        BottomCardContainer(
            navController = navController,
            homeViewModel = homeViewModel,
            travelInfo = travelInfo,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TravelReadyPager(
    cardHeight: Dp,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    travelInfo: TravelRoomInfoDto,
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
                    mainViewModel = mainViewModel,
                    homeViewModel = homeViewModel,
                    travelInfo = travelInfo,
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    totalDot = 4
                )
            }
            
            1 -> {
                GraphPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    totalDot = 4,
                    travelInfo = travelInfo,
                )
            }
            
            2 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "전체 내역",
                    totalDot = 4,
                    travelInfo = travelInfo,
                )
            }
            
            3 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "나의 전체 내역",
                    totalDot = 4,
                    travelInfo = travelInfo,
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TravelStartPager(
    cardHeight: Dp,
    travelInfo: TravelRoomInfoDto,
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
                    totalDot = 3,
                    travelInfo = travelInfo,
                )
            }
            
            1 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "전체 내역",
                    totalDot = 3,
                    travelInfo = travelInfo,
                )
            }
            
            2 -> {
                UseMoneyPage(
                    pagerState = pagerState,
                    cardHeight = cardHeight,
                    title = "나의 전체 내역",
                    totalDot = 3,
                    travelInfo = travelInfo,
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