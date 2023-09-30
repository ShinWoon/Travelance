package com.moneyminions.presentation.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.result.view.SettleResultCardView

@Composable
fun SettleResultReceiveScreen(
    navcontroller: NavController,
) {
    val result = "get"
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
//            TravelInfoView(
//                startDate = "2023/09/05",
//                endDate = "2023/09/07",
//                budget = 30000,
//                type = "result",
//                modifier = Modifier,
//                navController = navcontroller,
//            )
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
