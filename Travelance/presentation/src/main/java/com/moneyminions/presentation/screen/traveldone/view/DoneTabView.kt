package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoneTabView(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    selectedTabIndex: PagerState,
    tabWidths: MutableList<Dp>,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = selectedTabIndex.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .customTabIndicatorOffset(
                        tabPositions[selectedTabIndex.currentPage],
                        tabWidths[selectedTabIndex.currentPage],
                    ) // 넓이, 애니메이션 지정
                    .graphicsLayer {
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp,
                        )
                        clip = true
                    }, // 모양 지정
                color = PinkDarkest, // 색상 지정
            )
        },
        divider = {
            Divider(
                color = DividerDefaults.color,
                modifier = modifier.fillMaxWidth(),
                thickness = (0.5).dp,
            )
        },
        containerColor = Color.Transparent,
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex.currentPage == index,
                onClick = {
                    coroutineScope.launch {
//                        selectedTabIndex.value = index
                        selectedTabIndex.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface, // if (selectedTabIndex.value == index) PointDeepGreen else
                        style = if (selectedTabIndex.currentPage == index) CustomTextStyle.pretendardBold16 else CustomTextStyle.pretendardSemiBold16,
                        onTextLayout = { textLayoutResult ->
                            tabWidths[index] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        },
                    )
                },
            )
        }
    }
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

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp,
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    },
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = "",
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = "",
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart) // indicator 표시 위치
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}
