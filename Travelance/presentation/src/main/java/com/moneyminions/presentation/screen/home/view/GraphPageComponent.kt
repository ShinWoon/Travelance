package com.moneyminions.presentation.screen.home.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.moneyminions.domain.model.home.TravelRoomInfoDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.common.CustomTextStyle.pretendardLight12
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold16
import com.moneyminions.presentation.screen.home.DotsIndicator
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest
import com.moneyminions.presentation.utils.MoneyUtils
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GraphPage(
    pagerState: PagerState,
    cardHeight: Dp,
    totalDot: Int,
    travelInfo: TravelRoomInfoDto,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "사용 현황", style = pretendardBold16, letterSpacing = 1.sp)
                Text(text = "${MoneyUtils.makeComma(travelInfo.budget)}", style = pretendardBold16, letterSpacing = 0.5.sp)
            }
            
            Card(
                modifier = Modifier.weight(8f),
                colors = CardDefaults.cardColors(CardLightGray),
            ) {
                DonutGraph(travelInfo = travelInfo)
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(3f),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                MoneyAmountComponent(title = "사용 금액", money = "${MoneyUtils.makeComma(travelInfo.budget-travelInfo.rest)}")
                MoneyAmountComponent(title = "남은 금액", money = "${MoneyUtils.makeComma(travelInfo.rest)}")
            }
            
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                DotsIndicator(
                    totalDots = totalDot,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = PinkDarkest,
                    unSelectedColor = PinkLightest,
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DonutGraph(
    travelInfo: TravelRoomInfoDto,
) {
    
    val brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue))
    
    var animationProgress by remember { mutableStateOf(0f) }
    
    // 애니메이션을 정의합니다.
    val animationSpec = remember {
        infiniteRepeatable<Float>(
            animation = tween(durationMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    }
    
    val graphSize: Dp = 160.dp
    
    val progressValue = travelInfo.percent * 100
    LaunchedEffect(key1 = true) {
        while (true) {
            if (animationProgress >= progressValue) { // xx%까지만 그린 후 애니메이션 중지
                break
            }
            animationProgress += 1f
            delay(30)
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(graphSize.times(1.5f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = " ${animationProgress}%", style = pretendardBold20)
        Canvas(
            modifier = Modifier.size(graphSize),
        ) {
            val donutRadius = 209f
            val strokeWidth = 80f
            val center = Offset(size.width / 2, size.height / 2)
            
            val sweepAngle = 360 * animationProgress / 100
            val startAngle = 270f
            
            // 그라데이션
            val brush = Brush.verticalGradient(listOf(PinkDarkest, PinkLightest, PinkDarkest))
            
            // 내부 원
            drawCircle(
                color = GraphGray,
                radius = donutRadius,
                center = center,
                style = Stroke(strokeWidth)
            )
            
            // 원형 그래프
            drawArc(
//                color = PinkDarkest,
                brush = brush,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    strokeWidth,
                    cap = StrokeCap.Round
                ),
            )
        }
    }
}

@Composable
fun MoneyAmountComponent(
    title: String,
    money: String = "0원",
) {
    val dotColor = if (title == "사용 금액") {
        painterResource(id = R.drawable.ic_pink_dot)
    } else {
        painterResource(id = R.drawable.ic_lightgray_dot)
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = dotColor,
                contentDescription = "dot",
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = pretendardLight12,
                letterSpacing = 1.sp
            )
        }
        
        Text(text = money, style = pretendardSemiBold16, letterSpacing = 0.5.sp)
    }
}
