package com.moneyminions.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.common.text.CustomTextStyle.regularTextStyle
import com.moneyminions.presentation.common.text.CustomTextStyle.regularTitleTextStyle
import com.moneyminions.presentation.screen.home.view.MainComponentPageOne
import com.moneyminions.presentation.screen.home.view.TopComponent

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val scrollableState = rememberScrollState()
    
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollableState),
    ) {
        Home()
    }
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
    
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TopComponent()
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Pager
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            count = 3,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> MainComponentPageOne(pagerState)
                1 -> MainComponentPageOne(pagerState)  //todo 1, 2 페이지 변경 필요
                2 -> MainComponentPageOne(pagerState)
            }
        }
    
        Spacer(modifier = Modifier.height(16.dp))
        
        FriendComponent()

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
            .wrapContentHeight()
    
    ) {
        
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }
            
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}


@Composable
fun FriendComponent() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "친구들", style = regularTitleTextStyle)
            Text(text = "친구 추가", style = regularTextStyle)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
    
        /**
         * todo 사용자 정보로 처리해야함
         */
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) {
                MinionProfile()
            }
        }
    }

}
