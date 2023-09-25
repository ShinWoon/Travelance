package com.moneyminions.presentation.screen.traveldone

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.TravelInfoView

@Composable
fun TravelDoneScreen() {
    Column {
        TravelInfoView(
            startDate = "2023/09/05",
            endDate = "2023/09/07",
            budget = 30000,
            type = "done",
            modifier = Modifier,
            navController = rememberNavController(),
        )

    }
}
