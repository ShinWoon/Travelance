package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.moneyminions.presentation.screen.detail.view.DetailCommonTitleText

@Composable
fun SettleResultCardView(result: String) {
    Column {
        SettleResultUseDataView()
        DetailCommonTitleText(text = "멤버별 이체 금액")
        LazyColumn() {

        }
    }
}
