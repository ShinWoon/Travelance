package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.moneyminions.domain.model.home.TravelRoomInfoDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.LottieLoader
import com.moneyminions.presentation.screen.home.DotsIndicator
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.home.HomeViewModel

private const val TAG = "TravelReadyComponent_D210"
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TravelReadyComponent(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    travelInfo: TravelRoomInfoDto,
    pagerState: PagerState,
    cardHeight: Dp,
    totalDot: Int,
) {
    // 여행시작 다이얼로그 State
    var openTravelStartDialog by remember { mutableStateOf(false) }
    
    
    
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
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(9f)
            ) {
                LottieLoader(
                    modifier = Modifier.fillMaxSize(),
                    res = R.raw.lottie_travel_ready,
                    isLoop = true
                ) {}
            }
            
            StartButtonComponent(
                Modifier,
                homeViewModel
            ){
                openTravelStartDialog = true
            }
            
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                DotsIndicator(
                    totalDots = totalDot,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = PinkDarkest,
                    unSelectedColor = PinkLightest,
                )
            }
        }
        
        // 여행시작 확인 다이얼로그
        if(openTravelStartDialog) {
            TravelStartDialog(
                homeViewModel = homeViewModel,
                onDismiss = {openTravelStartDialog = false}
            )
        }
    }
}


@Composable
fun StartButtonComponent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    action:() -> Unit
) {
    Box(modifier = modifier.padding(horizontal = 8.dp)) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkDarkest,
                contentColor = Color.White,
            ),
            onClick = action
        ) {
            Text(
                text = "여행 시작",
                style = CustomTextStyle.pretendardBold20
            )
        }
    }
}