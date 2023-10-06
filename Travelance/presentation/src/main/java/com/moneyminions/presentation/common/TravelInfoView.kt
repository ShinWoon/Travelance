package com.moneyminions.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold18
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.utils.DateUtils
import com.moneyminions.presentation.utils.MoneyUtils.makeComma

private const val TAG = "D210"

@Composable
fun TravelInfoView(
    travelRoomInfo: TravelRoomInfoDto,
    type: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    roomId: Int = -1,
    setTravelRoomInfo: (TravelRoomInfoDto) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 16.dp),
    ) {
        if (type == "detail") {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    modifier = modifier
                        .size(24.dp)
                        .clickable {
                            Log.d(TAG, "TravelInfoView에서 travelInfo $travelRoomInfo")
                            setTravelRoomInfo(travelRoomInfo)
//                            navController.navigate("${Screen.TravelEdit.route}/$roomId")
                            navController.navigate(
                                "${Screen.TravelEdit.route}/{roomId}".replace(
                                    oldValue = "{roomId}",
                                    newValue = "$roomId",
                                ),
                            )
                        },
                    painter = painterResource(id = R.drawable.ic_edit),
                    tint = DarkerGray,
                    contentDescription = "edit icon",
                )
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calender),
                contentDescription = "detail calender icon",
                modifier = modifier.size(40.dp),
            )
            DetailDateView(startDate = travelRoomInfo.startDate, endDate = travelRoomInfo.endDate, modifier = modifier)
        }
        BudgetText(budget = travelRoomInfo.budget, type = type, modifier = modifier)
    }
}

@Composable
fun DetailDateView(
    startDate: String,
    endDate: String,
    modifier: Modifier,
) {
    Column(modifier = modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)) {
        DateText(text = "시작", date = DateUtils.DashToSlash(startDate))
        Spacer(modifier = modifier.height(2.dp))
        DateText(text = "종료", date = DateUtils.DashToSlash(endDate))
    }
}

@Composable
fun DateText(
    text: String,
    date: String,
) {
    Text(
        text = "$text : $date",
        color = DarkerGray,
        style = pretendardBold14,
    )
}

@Composable
fun BudgetText(
    budget: Int,
    type: String = "",
    modifier: Modifier,
) {
    Text(
        text = if (type == "done") "${makeComma(budget)}" else "예산:${makeComma(budget)}",
        color = DarkerGray,
        style = pretendardBold18,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
    )
}
