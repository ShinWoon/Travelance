package com.moneyminions.presentation.screen.traveldone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.presentation.common.CommonTabView
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.traveldone.view.DoneMembersView
import com.moneyminions.presentation.screen.traveldone.view.DonePublicMoneyView
import com.moneyminions.presentation.screen.traveldone.view.DoneTotalView
import com.moneyminions.presentation.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TravelDoneScreen(
    navController: NavHostController,
    roomId: Int,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("전체", "공금내역", "멤버들")
    var selectedTabIndex = rememberPagerState(pageCount = { tabs.size })
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    Scaffold (
        topBar = {
            TopBar(navController = navController, topBarTitle = "travelName")
        }
    ){
        Column(
            modifier = modifier.padding(it)
        ) {
            TravelInfoView(
                travelRoomInfo = TravelRoomInfoDto(),
                type = "done",
                modifier = modifier,
                navController = rememberNavController(),
                setTravelRoomInfo = {}
            )
            CommonTabView(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                tabWidths = tabWidths,
                modifier = modifier
            )
            HorizontalPager(
                state = selectedTabIndex,
                modifier = modifier
                    .fillMaxSize()
                    .background(color = White)
                    .padding(0.dp),
            ) { page ->
                when (page) {
                    0 -> DoneTotalView()
                    1 -> DonePublicMoneyView()
                    2 -> DoneMembersView()
                }
            }
        }
    }
}
