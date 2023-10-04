package com.moneyminions.presentation.screen.detail.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.utils.MoneyUtils

private const val TAG = "D210"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPublicMoneySettleContentView(
    modifier: Modifier = Modifier,
    publicMoneyPayment: TravelPaymentDto,
    changeValue: (TravelPaymentDto) -> Unit,
    deleteDialog: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        Log.d(TAG, "DetailPublicMoneySettleContentView: long clicked")
                        changeValue(TravelPaymentDto(isWithPaid = !(publicMoneyPayment.isWithPaid), paymentId = publicMoneyPayment.paymentId))
                        deleteDialog()
                    },
                ),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DetailCommonText(text = publicMoneyPayment.paymentContent)
                DetailCommonText(text = MoneyUtils.makeComma(publicMoneyPayment.paymentAmount))
            }
            Text(
                text = publicMoneyPayment.paymentAt,
                color = TextGray,
                style = pretendardSemiBold12,
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Divider(
            color = DividerDefaults.color,
            thickness = (0.5).dp,
        )
    }
}
