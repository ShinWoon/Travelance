package com.moneyminions.presentation.screen.traveldone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.traveldone.view.DoneTabView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TravelDoneScreen(
    navController: NavHostController,
    travelName: String
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
            TopBar(navController = navController, topBarTitle = travelName)
        }
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {
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
}
