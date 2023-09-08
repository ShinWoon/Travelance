package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.text.CustomTextStyle
import com.moneyminions.presentation.screen.home.DotsIndicator
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainComponentPageOne(pagerState: PagerState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "사용 현황", style = CustomTextStyle.smallTitleTextStyle)
                Text(text = "300,000원", style = CustomTextStyle.smallTitleTextStyle)
            }
            
            DoughnutChart()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MoneyAmountComponent(title = "사용 금액", money = "180,000원")
                MoneyAmountComponent(title = "남은 금액", money = "120,000원")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            DotsIndicator(
                totalDots = 3,
                selectedIndex = pagerState.currentPage,
                selectedColor = PinkDarkest,
                unSelectedColor = PinkLightest
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
        }
    }
}




// 도넛 차트
@Composable
fun DoughnutChart(
    values: List<Float> = listOf(65f, 40f, 25f, 20f),
    colors: List<Color> = listOf(
        Color(0xFFFF6384),
        Color(0xFFFFCE56),
        Color(0xFF36A2EB),
        Color(0xFF448AFF)
    ),
    size: Dp = 180.dp,
    thickness: Dp = 54.dp
) {
    // Sum of all the values
    val sumOfValues = values.sum()
    
    // Calculate each proportion
    val proportions = values.map {
        it * 100 / sumOfValues
    }
    
    // Convert each proportion to angle
    val sweepAngles = proportions.map {
        360 * it / 100
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(size.times(1.5f)),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {
            var startAngle = -90f
            
            for (i in values.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
                )
                startAngle += sweepAngles[i]
            }
        }
    }
}

@Composable
fun MoneyAmountComponent(
    title: String,
    money: String = "0원"
) {
    
    val dotColor = if(title == "사용 금액") painterResource(id = R.drawable.ic_pink_dot)
    else painterResource(id = R.drawable.ic_lightgray_dot)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = dotColor,
                contentDescription = "dot"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = CustomTextStyle.regularTextStyle
            )
        }
        
        Text(text = money, style = CustomTextStyle.littleTitleTextStyle)
    }
}