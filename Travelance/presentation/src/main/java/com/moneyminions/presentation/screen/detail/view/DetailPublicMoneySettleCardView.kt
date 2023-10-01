package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.pretendard

@Composable
fun DetailPublicMoneySettleCardView(
    modifier: Modifier = Modifier,
    publicMoneyList: List<TravelPaymentDto>,
    changeValue: (TravelPaymentDto) -> Unit,
    deleteDialog: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            if(publicMoneyList.isEmpty()) {
                item {
                    Row {
                        Text(
                            text = "아직 공금으로 설정한 내역이 없습니다",
                            color = DarkerGray,
                            style = pretendardBold14,
                            modifier = modifier.padding(vertical = 16.dp),
                            )
                    }
                }
            }
            itemsIndexed(publicMoneyList) { index, item ->
                DetailPublicMoneySettleContentView(
                    modifier = modifier,
                    publicMoneyPayment = item,
                    changeValue = changeValue,
                    deleteDialog = deleteDialog,
                )
            }
        }
    }
}
