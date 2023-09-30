package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardRegular10
import com.moneyminions.presentation.common.CustomTextStyle.pretendardRegular16
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.screen.detail.view.MemberView
import com.moneyminions.presentation.theme.BlueDarkest
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.DividerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun SettleResultTransferView(modifier: Modifier, result: String) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
//            MemberView(modifier = modifier)
            MemberTotalUsedMoney(modifier = modifier, result = result)
            Text(
                text = (if (result == "big") "+" else "-") + MoneyUtils.makeComma(50000),
                color = if (result == "big") BlueDarkest else PinkDarkest,
                style = pretendardRegular16,
            )
        }
        Spacer(modifier = modifier.height(4.dp))
        Divider(
            modifier = modifier,
            thickness = (0.5).dp,
            color = DividerDefaults.color
        )
    }
}

@Composable
fun MemberTotalUsedMoney(modifier: Modifier, result: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = MoneyUtils.makeComma(40000),
            color = DarkerGray,
            style = pretendardSemiBold14,
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = (if (result == "big") "-" else "+") + MoneyUtils.makeComma(20000),
            color = if (result == "big") BlueDarkest else PinkDarkest,
            style = pretendardRegular10,
        )
    }
}
