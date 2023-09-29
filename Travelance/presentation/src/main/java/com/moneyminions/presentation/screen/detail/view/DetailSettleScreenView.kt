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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.SettleCardView
import com.moneyminions.presentation.theme.DarkerGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSettleScreenView(
    modifier: Modifier = Modifier,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    // Modal Bottom Sheet이 닫힐 때 호출될 콜백
    if (showBottomSheet) {
        DetailMyPaymentDetailsView(onDismissSheet = {
            showBottomSheet = false
        })
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    showBottomSheet = true
                },
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
        SettleCardView()
        Spacer(modifier = modifier.height(16.dp))
        MinionPrimaryButton(
            content = "정산요청",
            modifier = modifier.fillMaxWidth(),
        ) {
            // todo: 정산 요청 버튼
        }
    }
}
