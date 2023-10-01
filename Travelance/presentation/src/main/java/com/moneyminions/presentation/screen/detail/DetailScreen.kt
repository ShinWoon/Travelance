package com.moneyminions.presentation.screen.detail

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
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
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.detail.view.DetailMemberScreenView
import com.moneyminions.presentation.screen.detail.view.DetailSettleScreenView
import com.moneyminions.presentation.screen.detail.view.DetailTabView
import com.moneyminions.presentation.screen.detail.view.PublicMoneyDeleteDialog
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.travel.TravelDetailViewModel

private const val TAG = "싸피"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    travelId: Int,
    modifier: Modifier = Modifier,
    travelDetailViewModel: TravelDetailViewModel = hiltViewModel(),
) {
    val myPaymentListState by travelDetailViewModel.myPaymentListState.collectAsState()
    var myPaymentList by remember {
        mutableStateOf(listOf<TravelPaymentDto>())
    }
    val travelDetailInfoState by travelDetailViewModel.travelDetailInfoState.collectAsState()
    var travelDetailInfo by remember {
        mutableStateOf(TravelDetailInfoDto())
    }

    var selectedTravelInfo by remember {
        mutableStateOf(TravelPaymentDto())
    }

    val updateTravelPaymentState by travelDetailViewModel.updateTravelPaymentInfoState.collectAsState()
    val setSettleStateState by travelDetailViewModel.setSettleStateState.collectAsState()

    var selectedIdx by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        travelDetailViewModel.getMyPaymentList()
        travelDetailViewModel.getTravelDetailInfo()
    }

    NetworkResultHandler(state = myPaymentListState, errorAction = { /*TODO*/ }, successAction = {
        myPaymentList = it
        Log.d(TAG, "DetailScreen: $myPaymentList")
    })
    NetworkResultHandler(
        state = travelDetailInfoState,
        errorAction = { /*TODO*/ },
        successAction = {
            travelDetailInfo = it
        })

    NetworkResultHandler(
        state = updateTravelPaymentState,
        errorAction = { /*TODO*/ },
        successAction = {
            travelDetailViewModel.getTravelDetailInfo()
        })

    NetworkResultHandler(state = setSettleStateState, errorAction = { /*TODO*/ }, successAction = {})

    val tabs = listOf("공금내역", "멤버내역")
    var selectedTabIndex = rememberPagerState(pageCount = { tabs.size })
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    var deleteDialog by remember { mutableStateOf(false) }
    if (deleteDialog) {
        PublicMoneyDeleteDialog(
            onDismissRequest = { deleteDialog = false },
            acceptRequest = {
                Log.d(TAG, "DetailSettleScreenView: accept")
                travelDetailViewModel.updateTravelPaymentInfo(
                    TravelPaymentChangeInfoDto(
                        paymentId = selectedTravelInfo.paymentId,
                        withPaid = selectedTravelInfo.isWithPaid
                    )
                )
                deleteDialog = false
            }
        )
    }
    Scaffold(
        // TODO: title 바꾸기
        topBar = {
            TopBar(navController = navController, topBarTitle = "test")
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            TravelInfoView(
                travelRoomInfo = travelDetailInfo.travelRoomInfo[0],
                type = "detail",
                modifier = Modifier,
                navController = navController,
            )
            DetailTabView(
                modifier = Modifier,
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                tabWidths = tabWidths,
                travelDetailViewModel = travelDetailViewModel,
            )
            HorizontalPager(
                state = selectedTabIndex,
                modifier = modifier
                    .fillMaxSize()
                    .background(color = White)
                    .padding(0.dp),
            ) { page ->
                when (page) {
                    0 -> DetailSettleScreenView(
                        publicMoneyList = travelDetailInfo.travelPayment,
                        myPaymentList = myPaymentList,
                        changeValue = {travelPaymentDto ->
                            Log.d(
                                TAG,
                                "DetailScreen: $travelPaymentDto"
                            )
                            selectedTravelInfo = travelPaymentDto
                        },
                        deleteDialog = { deleteDialog = true },
                        selectedIdx = selectedIdx,
                        myPaymentRowSelect = {paymentMap ->
                            selectedIdx = paymentMap["index"] as Int
                            selectedTravelInfo = TravelPaymentDto(
                                isWithPaid = paymentMap["isWithPaid"] as Boolean,
                                paymentId = paymentMap["paymentId"] as Int
                            )
                        },
                        myPaymentAccept = {
                            travelDetailViewModel.updateTravelPaymentInfo(
                                TravelPaymentChangeInfoDto(
                                    paymentId = selectedTravelInfo.paymentId,
                                    withPaid = selectedTravelInfo.isWithPaid
                                )
                            )
                            selectedIdx = -1
                        },
                        getMyPayment = {
                            travelDetailViewModel.getMyPaymentList()
                        },
                        setSettle = {
                            travelDetailViewModel.setSettleState(PaymentCompleteDto(paymentWithList = travelDetailInfo.travelPayment, roomNumber = travelId))
                        },
                    )
                    1 -> DetailMemberScreenView(friendPaymentList = travelDetailInfo.friendPayments)
                }
            }
        }
    }
}
