package com.moneyminions.presentation.screen.travellist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGrayMiddle
import com.moneyminions.presentation.theme.GreenMiddle
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.utils.MoneyUtils

@Composable
fun TravelCardView(
    modifier: Modifier,
    travelName: String,
    travelStart: String,
    travelEnd: String,
    done: String,
    moneyAmount: Int,
    iconId: Int,
) {
    val items = arrayOf(
        R.drawable.ic_airballoon,
        R.drawable.ic_camera,
        R.drawable.ic_compass,
        R.drawable.ic_flipflop,
        R.drawable.ic_luggage,
        R.drawable.ic_mountain,
        R.drawable.ic_music,
        R.drawable.ic_path,
        R.drawable.ic_rocket,
        R.drawable.ic_shoppingbag,
    )
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(vertical = 8.dp),
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
                TopTravelInfoView(
                    modifier = modifier,
                    travelName = travelName,
                    done = done,
                    iconId = iconId,
                )
                TravelDateView(
                    travelStart = travelStart,
                    travelEnd = travelEnd,
                    modifier = modifier,
                )
            }
            MoneyAmountView(moneyAmount = moneyAmount, modifier = modifier)
        }
    }
}

@Composable
fun TopTravelInfoView(
    modifier: Modifier,
    travelName: String,
    done: String,
    iconId: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TravelTitleView(travelName = travelName, iconId = iconId, modifier)
        SettlementStateView(done = done, modifier = modifier)
    }
}

@Composable
fun TravelTitleView(travelName: String, iconId: Int, modifier: Modifier) {
    Row(modifier = modifier.wrapContentWidth()) {
        Text(
            text = travelName,
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
        Text(text = travelStart)
        Spacer(modifier = modifier.width(8.dp))
        Text(text = "~")
        Spacer(modifier = modifier.width(8.dp))
        Text(text = travelEnd)
    }
}

@Composable
fun SettlementStateView(done: String, modifier: Modifier) {
    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (done) {
            "yet" -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    tint = DarkGrayMiddle,
                    contentDescription = "first settling",
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = "사전 정산 중",
                    color = DarkGrayMiddle,
                )
            }

            "doing" -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    tint = PinkDarkest,
                    contentDescription = "second settling",
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = "정산 중",
                    color = PinkDarkest,
                )
            }

            "done" -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    tint = GreenMiddle,
                    contentDescription = "settling done",
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = "사전 정산 중",
                    color = GreenMiddle,
                )
            }

            else -> {}
        }
    }
}

@Composable
fun MoneyAmountView(moneyAmount: Int, modifier: Modifier) {
    Spacer(modifier = modifier.height(32.dp))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Text(text = MoneyUtils.makeComma(moneyAmount))
    }
}

@Composable
@Preview(showBackground = true)
fun TravelCardPreview() {
    TravelCardView(
        modifier = Modifier,
        travelName = "룰루랄라",
        travelStart = "2023.07.23",
        travelEnd = "2023.07.30",
        done = "done",
        moneyAmount = 5500000,
        iconId = R.drawable.ic_camera,
    )
}
