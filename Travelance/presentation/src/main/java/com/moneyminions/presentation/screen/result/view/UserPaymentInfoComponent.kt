package com.moneyminions.presentation.screen.result.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldetail.SettleResultUserInfoDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.BlueMiddle
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.utils.DateUtils
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun UserPaymentInfoComponent(
    receiveInfos: List<SettleResultUserInfoDto>?,
    sendInfos: List<SettleResultUserInfoDto>?
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = "멤버별 이체 금액",
            style = CustomTextStyle.pretendardSemiBold16
        )
        if(receiveInfos!=null){
            LazyColumn() {
                items(receiveInfos.size) { idx ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            MinionProfile(size = 48.dp, img = receiveInfos[idx].profileUrl)

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "${receiveInfos[idx].nickName}",
                                style = CustomTextStyle.pretendardBold16,
                            )
                        }
                        Box(
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = MoneyUtils.makeCommaWon(receiveInfos[idx].paymentAmount) ,
                                style = CustomTextStyle.pretendardBold16.copy(
                                    color = BlueMiddle
                                ),
                            )
                        }
                    }
                }
            }
        }
        if(sendInfos!=null){
            LazyColumn() {
                items(sendInfos.size) { idx ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            MinionProfile(size = 48.dp, img = sendInfos[idx].profileUrl)

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "${sendInfos[idx].nickName}",
                                style = CustomTextStyle.pretendardBold16,
                            )
                        }

                        Box(
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = MoneyUtils.makeCommaWon(sendInfos[idx].paymentAmount) ,
                                style = CustomTextStyle.pretendardBold16.copy(
                                    color = PinkMiddle
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

}