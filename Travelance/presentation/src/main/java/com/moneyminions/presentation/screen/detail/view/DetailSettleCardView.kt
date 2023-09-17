package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailSettleCardView(modifier: Modifier, type: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(248.dp),
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            if (type == "together") {
                DetailCommonTitleText(text = "공금 내역")
            } else {
                DetailCommonTitleText(text = "결제 내역")
            }
            Spacer(modifier = modifier.height(8.dp))
            LazyColumn() {
                items(10) {
                    DetailSettleContentView(modifier = modifier, type = type)
                }
            }
        }
    }
}
