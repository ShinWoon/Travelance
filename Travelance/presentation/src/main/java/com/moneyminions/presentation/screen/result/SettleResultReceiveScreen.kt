package com.moneyminions.presentation.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.screen.detail.view.DetailTopInfoView
import com.moneyminions.presentation.screen.result.view.SettleResultCardView

@Composable
fun SettleResultReceiveScreen() {
    val result = "get"
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            DetailTopInfoView(
                startDate = "2023/09/05",
                endDate = "2023/09/07",
                budget = 30000,
                type = "result",
                modifier = Modifier,
            )
            SettleResultCardView(result = result, modifier = Modifier)
        }
        if (result == "get") {
            MinionPrimaryButton(content = "확인", modifier = Modifier) {
            }
        } else {
            MinionPrimaryButton(content = "이체", modifier = Modifier) {
            }
        }
    }
}
