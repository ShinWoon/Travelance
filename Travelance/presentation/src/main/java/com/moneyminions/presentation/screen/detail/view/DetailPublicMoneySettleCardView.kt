package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.SettleContentView
import com.moneyminions.presentation.theme.CardLightGray

@Composable
fun DetailPublicMoneySettleCardView(
    modifier: Modifier = Modifier,
    publicMoneyList: List<TravelPaymentDto>,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            items(publicMoneyList.size) {
                SettleContentView(
                    modifier = modifier,
                    payContent = publicMoneyList[it].paymentContent,
                    payDate = publicMoneyList[it].paymentAt,
                    payAmount = publicMoneyList[it].paymentAmount,
                )
            }
        }
    }
}
