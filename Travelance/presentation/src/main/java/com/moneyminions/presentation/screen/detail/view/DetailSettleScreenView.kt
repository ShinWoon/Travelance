package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.theme.pretendard

@Composable
fun DetailSettleScreenView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                modifier = modifier.wrapContentWidth(),
            ) {
                Text(
                    text = "공금 추가",
                    color = DarkerGray,
                    style = pretendardBold14,
                )
                Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    tint = DarkerGray,
                    modifier = modifier.size(24.dp),
                    contentDescription = "add settle button",
                )
            }

        }
        DetailSettleCardView(modifier = modifier, type = "together")
        Spacer(modifier = modifier.height(16.dp))
        MinionPrimaryButton(
            content = "정산요청",
            modifier = modifier.fillMaxWidth(),
        ) {
            // todo: 정산 요청 버튼
        }
    }
}
