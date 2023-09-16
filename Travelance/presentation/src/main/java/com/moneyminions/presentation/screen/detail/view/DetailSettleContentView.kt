package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.theme.BlueDarkest
import com.moneyminions.presentation.theme.DividerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun DetailSettleContentView(modifier: Modifier, type: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DetailCommonText(text = "사용내역")
            DetailCommonText(text = MoneyUtils.makeComma(189000))
            SettleEditButton(type = type, modifier = modifier)
        }
        Spacer(modifier = modifier.height(4.dp))
        Divider(
            color = DividerGray,
            modifier = modifier
                .fillMaxWidth()
                .height(1.dp),
        )
    }
}

@Composable
fun SettleEditButton(type: String, modifier: Modifier) {
    Button(
        colors = ButtonDefaults.buttonColors(if (type == "together") PinkDarkest else BlueDarkest),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.height(32.dp),
        onClick = { /*TODO*/ },
    ) {
        Text(
            text = if (type == "together") "제거" else "추가",
            color = White,
            style = pretendardBold12,
        )
    }
}
