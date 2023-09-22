package com.moneyminions.presentation.screen.mypage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CardFrame
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.viewmodel.mypage.MyPageViewModel

private const val TAG = "MyPageScreen D210"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageScreeen(
    navController: NavHostController,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState(pageCount = {
        myPageViewModel.cardList.size
    })
    var selectedIdx by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "D210님!", // 여기 유저의 닉네임이 들어가야함
                style = CustomTextStyle.pretendardBold24,
            )
            IconButton(onClick = {
                navController.navigate(Screen.Setting.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_setting),
                    tint = DarkerGray,
                    contentDescription = "setting",
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "카드 목록",
            style = CustomTextStyle.pretendardSemiBold12,
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
            val card = myPageViewModel.cardList[idx]
            Card() {
                // Card content
                CardFrame(
                    name = card.name,
                    number = card.number,
                    idx = card.idx,
                    company = card.company,
                    logo = card.logo,
                )
            }
        }
        LaunchedEffect(pagerState.currentPage) {
            selectedIdx = pagerState.currentPage
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MyPageScreenPreview() {
    MyPageScreeen(navController = rememberNavController())
}
