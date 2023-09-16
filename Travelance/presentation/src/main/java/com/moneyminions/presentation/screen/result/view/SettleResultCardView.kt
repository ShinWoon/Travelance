package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moneyminions.presentation.screen.detail.view.DetailCommonTitleText

@Composable
fun SettleResultCardView(
    result: String,
    modifier: Modifier = Modifier,
) {
    Column {
        SettleResultUseDataView(result = result)
        DetailCommonTitleText(text = "멤버별 이체 금액")
        LazyColumn() {
            items(5) {
                SettleResultTransferView(result = result, modifier = modifier)
            }
        }
    }
}
