package com.moneyminions.presentation.screen.travellist.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold18
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBoldMoney20
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.BlueDarkest
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.DarkGrayMiddle
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.GreenMiddle
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.utils.DateUtils
import com.moneyminions.presentation.utils.MoneyUtils
import com.moneyminions.presentation.viewmodel.MainViewModel

private const val TAG = "TravelCardView_D210"

@Composable
fun TravelCardView(
    modifier: Modifier,
    travelRoomDto: TravelRoomDto,
    iconId: Int,
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val travelName = "test"
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .clickable {
                mainViewModel.setSelectRoomId(travelRoomDto.roomId)
                Log.d(TAG, "selectRoomId: ${mainViewModel.selectRoomId.value}")
                when (travelRoomDto.isDone) {
                    "BEFORE" -> {
                        navController.navigate(Screen.SubHome.route)
                    }
                    "NOW" -> {
                        navController.navigate(Screen.Home.route)
                    }
                    "WAIT" -> {
                        navController.navigate(Screen.WaitHome.route)
                    }
                    "DONE" -> {
                        navController.navigate(
                            "${Screen.TravelDone.route}/{roomId}".replace(
                                oldValue = "{roomId}",
                                newValue = "${travelRoomDto.roomId}"
                            )
                        )
                    }
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    SettlementStateView(done = travelRoomDto.isDone, modifier = modifier)
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                
                TopTravelInfoView(
                    modifier = modifier,
                    travelName = travelRoomDto.travelName,
                    iconId = iconId,
                )
                TravelDateView(
                    travelStart = travelRoomDto.startDate,
                    travelEnd = travelRoomDto.endDate,
                    modifier = modifier,
                )
            }
            MoneyAmountView(
                moneyAmount = travelRoomDto.budget,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun TopTravelInfoView(
    modifier: Modifier = Modifier,
    travelName: String,
    iconId: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TravelTitleView(travelName = travelName, iconId = iconId, modifier)
    }
}

@Composable
fun TravelTitleView(travelName: String, iconId: Int, modifier: Modifier) {
    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = travelName,
            style = pretendardBold18,
            color = DarkerGray,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = modifier.width(8.dp))
        Image(
            modifier = modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "travel card icon",
        )
    }
}

@Composable
fun TravelDateView(travelStart: String, travelEnd: String, modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Spacer(modifier = modifier.height(8.dp))
        DateText(txt = travelStart)
        Spacer(modifier = modifier.width(8.dp))
        DateText(txt = "~")
        Spacer(modifier = modifier.width(8.dp))
        DateText(txt = travelEnd)
    }
}

@Composable
fun DateText(txt: String) {
    Text(
        text = DateUtils.DashToDot(txt),
        style = pretendardSemiBold12,
        color = DarkGray,
    )
}

@Composable
fun SettlementStateView(done: String, modifier: Modifier) {
    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (done) {
            "BEFORE" -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    tint = DarkGrayMiddle,
                    contentDescription = "first settling",
                )
                Spacer(modifier = modifier.width(4.dp))
                StateText(
                    text = "사전 정산 중",
                    color = DarkGrayMiddle,
                )
            }

            "NOW" -> {
                RippleLoadingAnimation(modifier = modifier, circleColor = PinkDarkest)
                StateText(
                    text = "정산 중",
                    color = PinkDarkest,
                )
            }
            "WAIT" -> {
                RippleLoadingAnimation(modifier = modifier, circleColor = BlueDarkest)
                StateText(
                    text = "정산 요청 상태",
                    color = BlueDarkest,
                )
            }
            "DONE" -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    tint = GreenMiddle,
                    contentDescription = "settling done",
                )
                Spacer(modifier = modifier.width(4.dp))
                StateText(
                    text = "정산 완료",
                    color = GreenMiddle,
                )
            }

            else -> {}
        }
    }
}

@Composable
fun StateText(text: String, color: Color) {
    Text(
        text = text,
        style = pretendardBold14,
        color = color,
    )
}

@Composable
fun MoneyAmountView(moneyAmount: Int, modifier: Modifier) {
    Spacer(modifier = modifier.height(32.dp))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = MoneyUtils.makeComma(moneyAmount),
            style = pretendardBoldMoney20,
            color = DarkerGray,
            letterSpacing = 0.5.sp
        )
    }
}

// @Composable
// @Preview(showBackground = true)
// fun TravelCardPreview() {
//    TravelCardView(
//        modifier = Modifier,
//        travelName = "룰루랄라",
//        travelStart = "2023.07.23",
//        travelEnd = "2023.07.30",
//        done = "done",
//        moneyAmount = 5500000,
//        iconId = R.drawable.ic_travel_2,
//        navController = rememberNavController()
//    )
// }
