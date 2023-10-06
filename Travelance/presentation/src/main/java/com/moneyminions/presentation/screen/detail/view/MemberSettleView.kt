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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.FriendPaymentDto
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold10
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.Gray
import com.moneyminions.presentation.theme.GreenDark
import com.moneyminions.presentation.theme.GreenMiddle
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.PinkLightest
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun MemberSettleView(
    modifier: Modifier = Modifier,
    friendPayment: FriendPaymentDto,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MemberView(friendPayment = friendPayment)
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
    friendPayment: FriendPaymentDto
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MinionProfile(size = 48.dp, img = friendPayment.profileUrl)
        Spacer(modifier = modifier.width(16.dp))
        Column {
            SettleStateText(state = friendPayment.done)
            Spacer(modifier = modifier.height(4.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DetailCommonText(text = friendPayment.nickName)
                DetailCommonText(text = MoneyUtils.makeComma(friendPayment.paymentAmount))
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
                color = if (state) GreenMiddle.copy(0.4f) else PinkLightest.copy(0.8f),
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        Text(
            text = if (state) "요청완료" else "요청중",
            color = if (state) GreenDark else PinkDarkest,
            style = pretendardBold12,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 6.dp)
        )
    }
}
