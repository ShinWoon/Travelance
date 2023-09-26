package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold10
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.theme.CategoryAlcohol
import com.moneyminions.presentation.theme.CategoryCoffee
import com.moneyminions.presentation.theme.CategoryDining
import com.moneyminions.presentation.theme.CategoryLeisure
import com.moneyminions.presentation.theme.CategoryShopping
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.theme.pretendard
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CategoryGraphView(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PieChartView()
            Spacer(modifier = modifier.width(32.dp))
            CategoryInfo()
        }
    }
}

@Composable
fun CategoryInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        CategoryChips(color = CategoryDining, text = "식비")
        Spacer(modifier = modifier.height(8.dp))
        CategoryChips(color = CategoryDining, text = "식비")
        Spacer(modifier = modifier.height(8.dp))
        CategoryChips(color = CategoryDining, text = "식비")
        Spacer(modifier = modifier.height(8.dp))
        CategoryChips(color = CategoryDining, text = "식비")
        Spacer(modifier = modifier.height(8.dp))
        CategoryChips(color = CategoryDining, text = "식비")
    }
}

@Composable
fun CategoryChips(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {
    Row {
        Box(
            modifier = modifier
                .size(20.dp)
                .background(color = color, shape = CircleShape),
        )
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = text,
            color = DarkerGray,
            style = pretendardBold12,
        )
    }
}

@Composable
fun PieChartView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(240.dp)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        val chartDataList = listOf(
            ChartData(CategoryDining, 10f),
            ChartData(CategoryCoffee, 20f),
            ChartData(CategoryAlcohol, 15f),
            ChartData(CategoryLeisure, 5f),
            ChartData(CategoryShopping, 50f),
        )

        val textMeasurer = rememberTextMeasurer()
        val textMeasureResults = remember(chartDataList) {
            chartDataList.map {
                textMeasurer.measure(
                    text = "${it.data.toInt()}%",
                    style = pretendardSemiBold12
                )
            }
        }
        Canvas(
            modifier = modifier
                .size(240.dp)
        ) {
            val width = size.width
            val radius = width / 2f
            val strokeWidth = radius * .4f
            val innerRadius = radius - strokeWidth
            val lineStrokeWidth = 3.dp.toPx()

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {

                val chartData = chartDataList[index]
                val sweepAngle = chartData.data.asAngle
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle
                val textMeasureResult = textMeasureResults[index]
                val textSize = textMeasureResult.size

                drawArc(
                    color = chartData.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(width - strokeWidth, width - strokeWidth),
                    style = Stroke(strokeWidth)
                )

                rotate(
                    90f + startAngle
                ) {
                    drawLine(
                        color = Color.White,
                        start = Offset(radius, strokeWidth),
                        end = Offset(radius, 0f),
                        strokeWidth = lineStrokeWidth
                    )
                }

                val textCenter = textSize.center

                drawText(
                    textLayoutResult = textMeasureResult,
                    color = Color.Gray,
                    topLeft = Offset(
                        (-textCenter.x + center.x + (innerRadius + strokeWidth / 2) * cos(
                            angleInRadians
                        )).toFloat(),
                        (-textCenter.y + center.y + (innerRadius + strokeWidth / 2) * sin(
                            angleInRadians
                        )).toFloat()
                    )
                )

                startAngle += sweepAngle
            }
        }
    }
}

private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

private val Float.asAngle: Float
    get() = this * 360f / 100f

@Immutable
data class ChartData(val color: Color, val data: Float)
