package com.moneyminions.presentation.screen.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moneyminions.presentation.screen.detail.view.DetailTabView
import com.moneyminions.presentation.common.DetailTopInfoView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
) {
//    var selectedTabIndex = remember { mutableStateOf(0) }
    val tabs = listOf("전체", "멤버", "정산")
    var selectedTabIndex = rememberPagerState(pageCount = { tabs.size })
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    Column {
        DetailTopInfoView(
            startDate = "2023/09/05",
            endDate = "2023/09/07",
            budget = 30000,
            type = "detail",
            modifier = Modifier,
            navController = navController,
        )
        DetailTabView(
            modifier = Modifier,
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            tabWidths = tabWidths,
        )
    }
}
