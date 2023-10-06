package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.CategoryExpenseDto
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.CategoryAccommodation
import com.moneyminions.presentation.theme.CategoryAlcohol
import com.moneyminions.presentation.theme.CategoryCoffee
import com.moneyminions.presentation.theme.CategoryDining
import com.moneyminions.presentation.theme.CategoryGroceries
import com.moneyminions.presentation.theme.CategoryLeisure
import com.moneyminions.presentation.theme.CategoryMinimarts
import com.moneyminions.presentation.theme.CategoryShopping
import com.moneyminions.presentation.theme.CategoryTransportation
import com.moneyminions.presentation.theme.CategoryUncategorized
import com.moneyminions.presentation.theme.DarkerGray

private const val TAG = "D210"

@Composable
fun CategoryGraphView(
    modifier: Modifier = Modifier,
    categoryExpenseList: List<CategoryExpenseDto>,
) {
    Card(
        modifier = modifier.fillMaxWidth().height(240.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryChart(categoryExpenseList = categoryExpenseList)
            Spacer(modifier = modifier.width(40.dp))
            CategoryInfo(categoryExpenseList = categoryExpenseList)
        }
    }
}

@Composable
fun CategoryInfo(
    modifier: Modifier = Modifier,
    categoryExpenseList: List<CategoryExpenseDto>,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight().padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        itemsIndexed(categoryExpenseList) { _, item ->
            CategoryChips(color = getCategoryColor(item.category), text = item.category)
            Spacer(modifier = modifier.height(12.dp))
        }
    }
}

@Composable
fun CategoryChips(
    modifier: Modifier = Modifier,
    color: Color,
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .size(8.dp)
                .background(color = color, shape = CircleShape),
        )
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = text,
            color = DarkerGray,
            style = pretendardSemiBold12,
        )
    }
}

@Composable
private fun CategoryChart(
    categoryExpenseList: List<CategoryExpenseDto>,
) {
    val animatable = remember {
        Animatable(-90f)
    }

    val finalValue = 270f

    LaunchedEffect(key1 = animatable) {
        animatable.animateTo(
            targetValue = finalValue,
            animationSpec = tween(
                delayMillis = 500,
                durationMillis = 1200,
            ),
        )
    }
    val currentSweepAngle = animatable.value

    Box(
        modifier = Modifier
            .size(186.dp),
        contentAlignment = Alignment.Center,
    ) {
        val chartDataList = mutableListOf<ChartData>()

        for (expense in categoryExpenseList) {
            val color = getCategoryColor(expense.category)
            var data = expense.percent.toFloat() * 100
            if (data < 1f) data = 1f
            val chartData = ChartData(color, data)
            chartDataList.add(chartData)
        }

        Canvas(
            modifier = Modifier
                .size(186.dp)
                .aspectRatio(1f),
        ) {
            val width = size.width
            val radius = width / 2f
            val strokeWidth = radius * .4f
            val lineStrokeWidth = 3.dp.toPx()

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {
                val chartData = chartDataList[index]
                val sweepAngle = chartData.data.asAngle
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle

                if (startAngle <= currentSweepAngle) {
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle.coerceAtMost(currentSweepAngle - startAngle),
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth),
                    )
                }

                rotate(
                    90f + startAngle,
                ) {
                    drawLine(
                        color = CardLightGray,
                        start = Offset(radius, strokeWidth),
                        end = Offset(radius, 0f),
                        strokeWidth = lineStrokeWidth,
                    )
                }
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

private fun getCategoryColor(category: String): Color {
    return when (category) {
        "식비" -> CategoryDining
        "커피와 디저트" -> CategoryCoffee
        "주류" -> CategoryAlcohol
        "마트" -> CategoryGroceries
        "편의점" -> CategoryMinimarts
        "교통/자동차" -> CategoryTransportation
        "숙소" -> CategoryAccommodation
        "쇼핑" -> CategoryShopping
        "레저" -> CategoryLeisure
        else -> CategoryUncategorized
    }
}
