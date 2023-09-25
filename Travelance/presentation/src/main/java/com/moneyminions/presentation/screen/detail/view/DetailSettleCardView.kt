package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray

@Composable
fun DetailSettleCardView(modifier: Modifier, type: String) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(10) {
                DetailSettleContentView(
                    modifier = modifier,
                    payDate = "2023-09-23 12:51",
                    icon = R.drawable.ic_clear,
                    iconColor = TextGray
                )
            }
        }
    }
}
