package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.Gray
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight

@Composable
fun DonePublicMoneyView(
    modifier: Modifier = Modifier,
    allTravelPaymentList: List<TravelPaymentDto>,
    myPaymentList: List<TravelPaymentDto>,
) {
    var switchChecked by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (switchChecked) "내 내역" else "전체 내역",
                color = DarkerGray,
                style = pretendardBold12,
            )
            Spacer(modifier = modifier.width(8.dp))
            Switch(
                checked = switchChecked,
                onCheckedChange = {
                    switchChecked = it
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = PinkDarkest,
                    checkedTrackColor = PinkLight,
                    uncheckedThumbColor = Gray,
                    uncheckedTrackColor = LightGray,
                    uncheckedBorderColor = LightGray,
                    disabledCheckedThumbColor = Gray,
                ),
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        if (switchChecked) {
            TravelDoneSettleCardView(travelPaymentList = myPaymentList, modifier = Modifier.weight(9f))
        } else {
            TravelDoneSettleCardView(travelPaymentList = allTravelPaymentList, modifier = Modifier.weight(9f))
        }
    }
}
