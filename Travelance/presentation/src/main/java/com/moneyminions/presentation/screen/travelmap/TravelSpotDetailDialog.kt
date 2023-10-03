package com.moneyminions.presentation.screen.travelmap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.detail.view.DetailCommonText
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
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.MoneyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelSpotDetailDialog(
    spotInfoList: List<TravelMapDetailDto>,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            colors = CardDefaults.cardColors(getBackgroundColor(category = spotInfoList[0].category).copy(alpha = 0.7f))
        ) {
            LazyColumn() {
                itemsIndexed(spotInfoList){index, item ->
                    TravelSpotDetailContentView(
                        spotInfo = item,
                    )
                }
            }
        }
    }
}

@Composable
fun TravelSpotDetailContentView(
    modifier: Modifier = Modifier,
    spotInfo: TravelMapDetailDto,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
    ) {
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DetailCommonText(text = spotInfo.content)
            DetailCommonText(text = MoneyUtils.makeComma(spotInfo.price))
        }
        Text(
            text = spotInfo.paymentAt,
            color = White.copy(alpha = 0.8f),
            style = CustomTextStyle.pretendardSemiBold12,
        )
        Spacer(modifier = modifier.height(16.dp))
//        Divider(
//            color = DividerDefaults.color,
//            thickness = (0.5).dp,
//        )
    }
}

private fun getBackgroundColor(category: String): Color {
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