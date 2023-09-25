package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold10
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun MemberSettleView(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MemberView()
        Spacer(modifier = modifier.height(16.dp))
        Divider(
            modifier = modifier.fillMaxWidth(),
            thickness = (0.5).dp,
            color = DividerDefaults.color
        )
    }
}

@Composable
fun MemberView(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MinionProfile(54.dp)
        Spacer(modifier = modifier.width(16.dp))
        Column {
            SettleStateText(state = true)
            Spacer(modifier = modifier.height(4.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DetailCommonText(text = "사용자")
                DetailCommonText(text = MoneyUtils.makeComma(189000))
            }
        }
    }
}

@Composable
fun SettleStateText(
    state: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = if (state) PinkDarkest.copy(0.9f) else TextGray,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        Text(
            text = if (state) "정산요청완료" else "정산요청중",
            color = if (state) LightGray else DarkerGray,
            style = pretendardSemiBold10,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemberSettlePreview() {
    MemberSettleView(modifier = Modifier)
}
