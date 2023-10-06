package com.moneyminions.presentation.screen.traveldone.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.detail.view.DetailCommonText
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.utils.MoneyUtils

private const val TAG = "D210"

@Composable
fun TravelDoneSettleContentView(
    modifier: Modifier = Modifier,
    travelMoneyPayment: TravelPaymentDto,
) {
    Log.d(TAG, "TravelDoneSettleContentView: $travelMoneyPayment")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DetailCommonText(text = travelMoneyPayment.paymentContent)
            DetailCommonText(text = MoneyUtils.makeComma(travelMoneyPayment.paymentAmount))
        }
        Text(
            text = travelMoneyPayment.paymentAt,
            color = TextGray,
            style = CustomTextStyle.pretendardSemiBold12,
        )
    }
    Spacer(modifier = modifier.height(16.dp))
    Divider(
        color = DividerDefaults.color,
        thickness = (0.5).dp,
    )
}
