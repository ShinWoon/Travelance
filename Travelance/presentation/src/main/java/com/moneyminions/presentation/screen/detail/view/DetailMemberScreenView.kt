package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.FriendPaymentDto
import com.moneyminions.presentation.theme.CardLightGray

@Composable
fun DetailMemberScreenView(
    modifier: Modifier = Modifier,
    friendPaymentList: List<FriendPaymentDto> = listOf(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
    ) {
        Card(
            modifier = modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(CardLightGray),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                items(friendPaymentList.size) {
                    MemberSettleView(modifier = modifier, friendPayment = friendPaymentList[it])
                }
            }
        }
    }
}
