package com.moneyminions.presentation.screen.traveldone

import android.util.Log
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
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import com.moneyminions.presentation.common.CommonTabView
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.traveldone.view.DoneAnnouncementDialog
import com.moneyminions.presentation.screen.traveldone.view.DoneMembersView
import com.moneyminions.presentation.screen.traveldone.view.DonePublicMoneyView
import com.moneyminions.presentation.screen.traveldone.view.DoneTotalView
import com.moneyminions.presentation.screen.travelmap.TravelMapScreen
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.traveldone.TravelDoneViewModel

private const val TAG = "D210"

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
    var noticeInfoDialog by remember {
        mutableStateOf(false)
    }
    var selectedNoticeInfo by remember {
        mutableStateOf(NoticeAllDto())
    }
    NetworkResultHandler(
        state = travelDoneInfoGetState,
        errorAction = { /*TODO*/ },
        successAction = {
            travelDoneTotalInfo = it
            Log.d(TAG, "TravelDoneScreen: $travelDoneTotalInfo")
        },
    )
    LaunchedEffect(Unit) {
        travelDoneViewModel.getTravelDoneInfo(roomId = roomId)
    }

    if (noticeInfoDialog) {
        DoneAnnouncementDialog(
            noticeInfo = selectedNoticeInfo, onDismissRequest = {
                noticeInfoDialog = false
            },
            linkClick = {
                val url = selectedNoticeInfo.link
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "data",
                    value = url,
                )
                navController.navigate(Screen.WebView.route)
            },
        )
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
            TopBar(
                navController = navController,
                topBarTitle = travelDoneTotalInfo.travelDoneInfoDto.travelName,
            )
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
                        noticeAllInfo = travelDoneTotalInfo.noticeAllDtoList,
                        categoryExpenseList = travelDoneTotalInfo.categoryExpenseDtoList,
                        clickedNotice = {
                            selectedNoticeInfo = it
                            Log.d(TAG, "TravelDoneScreen: $it")
                        },
                        clickAction = {
                            noticeInfoDialog = true
                        },
                        moveToMap = {
                            navController.navigate("${Screen.TravelMap.route}/{roomId}/{type}".replace(oldValue = "{roomId}/{type}", newValue = "$roomId/{done}"))
                        },
                        showMap = {
                            TravelMapScreen(roomId = roomId, type = "done")
                        },
                    )

                    1 -> DonePublicMoneyView(
                        allTravelPaymentList = travelDoneTotalInfo.allTravelPaymentDto,
                        myPaymentList = travelDoneTotalInfo.myPaymentDtoList,
                    )

                    2 -> DoneMembersView(
                        memberInfoList = travelDoneTotalInfo.roomUserDtoList,
                    )
                }
            }
        }
    }
}
