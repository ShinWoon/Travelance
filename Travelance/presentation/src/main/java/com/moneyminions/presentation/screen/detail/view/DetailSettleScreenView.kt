package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.MinionPrimaryButton

@Composable
fun DetailSettleScreenView(modifier: Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = modifier.height(16.dp))
        DetailSettleCardView(modifier = modifier, type = "together")
        Spacer(modifier = modifier.height(16.dp))
        DetailSettleCardView(modifier = modifier, type = "mine")
        Spacer(modifier = modifier.height(16.dp))
        MinionPrimaryButton(
            content = "정산요청",
            modifier = modifier,
        ) {
            // todo: 정산 요청 버튼
        }
    }
}
