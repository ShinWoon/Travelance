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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import com.moneyminions.presentation.common.CommonTabView
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.traveldone.view.DoneMembersView
import com.moneyminions.presentation.screen.traveldone.view.DonePublicMoneyView
import com.moneyminions.presentation.screen.traveldone.view.DoneTotalView
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.traveldone.TravelDoneViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TravelDoneScreen(
    navController: NavHostController,
    roomId: Int,
    modifier: Modifier = Modifier,
    travelDoneViewModel: TravelDoneViewModel = hiltViewModel(),
) {
    val travelDoneInfoGetState by travelDoneViewModel.travelDoneInfoGetState.collectAsState()
    var travelDoneTotalInfo by remember {
        mutableStateOf(TravelDoneInfoTotalDto())
    }
    NetworkResultHandler(state = travelDoneInfoGetState, errorAction = { /*TODO*/ }, successAction = {
        travelDoneTotalInfo = it
    })
    LaunchedEffect(Unit) {
        travelDoneViewModel.getTravelDoneInfo(roomId = roomId)
    }

    val tabs = listOf("전체", "공금내역", "멤버들")
    var selectedTabIndex = rememberPagerState(pageCount = { tabs.size })
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    Scaffold(
        topBar = {
            TopBar(navController = navController, topBarTitle = travelDoneTotalInfo.travelDoneInfoDto.travelName)
        },
    ) {
        Column(
            modifier = modifier.padding(it),
        ) {
            TravelInfoView(
                travelRoomInfo = travelDoneTotalInfo.travelDoneInfoDto,
                type = "done",
                modifier = modifier,
                navController = rememberNavController(),
                setTravelRoomInfo = {},
            )
            CommonTabView(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                tabWidths = tabWidths,
                modifier = modifier,
            )
            HorizontalPager(
                state = selectedTabIndex,
                modifier = modifier
                    .fillMaxSize()
                    .background(color = White)
                    .padding(0.dp),
            ) { page ->
                when (page) {
                    0 -> DoneTotalView(
                        roomId = roomId,
                        noticeAllInfo = travelDoneTotalInfo.noticeAllDtoList,
                        categoryExpenseList = travelDoneTotalInfo.categoryExpenseDtoList,
                    )
                    1 -> DonePublicMoneyView(
                        allTravelPaymentList = travelDoneTotalInfo.allTravelPaymentDto,
                        myPaymentList = travelDoneTotalInfo.myPaymentDtoList,
                    )
                    2 -> DoneMembersView()
                }
            }
        }
    }
}
