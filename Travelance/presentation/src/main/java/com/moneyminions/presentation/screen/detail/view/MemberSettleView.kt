package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun MemberSettleView(modifier: Modifier) {
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
            MemberView(modifier = modifier)
            DetailCommonText(text = MoneyUtils.makeComma(189000))
            SettleStateText(state = false)
        }
        Spacer(modifier = modifier.height(8.dp))
        Divider(
            modifier = modifier,
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
    }
}

@Composable
fun MemberView(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MinionProfile(32.dp)
        Spacer(modifier = modifier.width(8.dp))
        DetailCommonText(text = "사용자")
    }
}

@Composable
fun SettleStateText(state: Boolean) {
    Text(
        text = if (state) "정산 요청 완료" else "정산 요청 중",
        color = if (state) PinkDarkest else TextGray,
        style = pretendardSemiBold12,
    )
}

@Preview(showBackground = true)
@Composable
fun MemberSettlePreview() {
    MemberSettleView(modifier = Modifier)
}
