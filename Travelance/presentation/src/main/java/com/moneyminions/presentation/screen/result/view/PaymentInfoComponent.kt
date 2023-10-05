package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.SettleResultPaymentInfoDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.BlueMiddle
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun PaymentInfoComponenet(
    settleResultPaymentInfoDto: SettleResultPaymentInfoDto?
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "총 사용량",
                style = CustomTextStyle.pretendardSemiBold16
            )
            Text(
                text = MoneyUtils.makeComma(settleResultPaymentInfoDto?.totalAmount?:0),
                style = CustomTextStyle.pretendardSemiBold16
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "인당",
                style = CustomTextStyle.pretendardSemiBold16
            )
            Text(
                text = MoneyUtils.makeComma(settleResultPaymentInfoDto?.perAmount?:0),
                style = CustomTextStyle.pretendardSemiBold16
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "내가 쓴 금액",
                style = CustomTextStyle.pretendardSemiBold16
            )
            Text(
                text = MoneyUtils.makeComma(settleResultPaymentInfoDto?.myAmount?:0),
                style = CustomTextStyle.pretendardSemiBold16
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "총 이체 금액",
                style = CustomTextStyle.pretendardSemiBold16
            )
            Text(
                text = if((settleResultPaymentInfoDto?.transferTotalAmount?:0)>=0) "-${MoneyUtils.makeComma(settleResultPaymentInfoDto?.transferTotalAmount?:0)}"
                        else "+${MoneyUtils.makeComma(Math.abs(settleResultPaymentInfoDto?.transferTotalAmount?:0))}",
                style = CustomTextStyle.pretendardSemiBold16.copy(
                    color = if((settleResultPaymentInfoDto?.transferTotalAmount?:0)>=0) PinkMiddle else BlueMiddle
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PaymentInfoComponentPreview(){
    PaymentInfoComponenet(
        settleResultPaymentInfoDto = SettleResultPaymentInfoDto(
            myAmount = 500000,
            perAmount = 500000,
            totalAmount = 500000,
            transferTotalAmount = 500000
    ))
}