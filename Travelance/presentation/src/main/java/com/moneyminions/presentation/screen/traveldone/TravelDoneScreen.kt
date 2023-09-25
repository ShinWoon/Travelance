package com.moneyminions.presentation.screen.traveldone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.traveldone.view.DoneTabView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TravelDoneScreen() {
    val tabs = listOf("전체", "공금내역", "멤버들")
    var selectedTabIndex = rememberPagerState(pageCount = { tabs.size })
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    Column {
        TravelInfoView(
            startDate = "2023/09/05",
            endDate = "2023/09/07",
            budget = 30000,
            type = "done",
            modifier = Modifier,
            navController = rememberNavController(),
        )
        DoneTabView(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            tabWidths = tabWidths,
        )
    }
}
