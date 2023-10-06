package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun DetailMyPaymentSettleContentView(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    paymentInfo: TravelPaymentDto,
    myPaymentRowSelect: (MutableMap<String, Any>) -> Unit,
    idx: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(
            modifier = modifier
                .background(color = backgroundColor)
                .clickable(
                    onClick = {
                        myPaymentRowSelect(
                            mutableMapOf(
                                "index" to idx,
                                "isWithPaid" to !paymentInfo.isWithPaid,
                                "paymentId" to paymentInfo.paymentId
                            )
                        )
                    }
                )
        ) {
            Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DetailCommonText(text = paymentInfo.paymentContent)
                DetailCommonText(text = MoneyUtils.makeComma(paymentInfo.paymentAmount))
            }
            Text(
                text = paymentInfo.paymentAt,
                color = TextGray,
                style = CustomTextStyle.pretendardSemiBold12,
            )
            Spacer(modifier = modifier.height(16.dp))
            Divider(
                color = DividerDefaults.color,
                thickness = (0.5).dp,
            )
        }
    }
}