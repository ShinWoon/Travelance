package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.theme.BlueDarkest
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun SettleResultUseDataView(
    modifier: Modifier = Modifier,
    result: String,
) {
    Column {
        SettleResultCommonInfo(
            text = "총 사용량",
            amount = 360000,
            result = result,
            modifier = modifier,
        )
        SettleResultCommonInfo(text = "인당", amount = 60000, result = result, modifier = modifier)
        SettleResultCommonInfo(
            text = "내가 쓴 금액",
            amount = 90000,
            result = result,
            modifier = modifier,
        )
        SettleResultCommonInfo(
            text = "총 이체 금액",
            amount = 30000,
            result = result,
            modifier = modifier,
        )
    }
}

@Composable
fun SettleResultCommonInfo(
    text: String,
    amount: Int,
    result: String,
    modifier: Modifier,
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SettleCommonText(text = text, color = DarkerGray)
            SettleCommonText(
                text = if (text == "총 이체 금액") {
                    (if (result == "big") "+" else "-") + MoneyUtils.makeComma(amount)
                } else {
                    MoneyUtils.makeComma(amount)
                },
                color = if (text == "총 이체 금액") {
                    if (result == "big") BlueDarkest else PinkDarkest
                } else {
                    DarkerGray
                },
            )
        }
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Composable
fun SettleCommonText(
    text: String,
    color: Color,
) {
    Text(
        text = text,
        color = color,
        style = pretendardBold16,
    )
}
