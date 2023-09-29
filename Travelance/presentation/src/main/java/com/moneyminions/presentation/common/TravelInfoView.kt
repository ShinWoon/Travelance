package com.moneyminions.presentation.common

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold18
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.utils.MoneyUtils.makeComma

private const val TAG = "싸피"

@Composable
fun TravelInfoView(
    startDate: String,
    endDate: String,
    budget: Int,
    type: String,
    modifier: Modifier = Modifier,
    navController: NavController,
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
                            navController.navigate(Screen.CreateTravel.route)
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
            DetailDateView(startDate = startDate, endDate = endDate, modifier = modifier)
            BudgetText(budget = budget, modifier = modifier)
        }
    }
}

@Composable
fun DetailDateView(
    startDate: String,
    endDate: String,
    modifier: Modifier,
) {
    Column(modifier = modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)) {
        DateText(text = "시작", date = startDate)
        Spacer(modifier = modifier.height(2.dp))
        DateText(text = "종료", date = endDate)
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
    modifier: Modifier,
) {
    Text(
        text = "예산:${makeComma(budget)}",
        color = DarkerGray,
        style = pretendardBold18,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
    )
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TravelInfoView(
        startDate = "2023/09/05",
        endDate = "2023/09/07",
        budget = 30000,
        type = "detail",
        modifier = Modifier,
        navController = rememberNavController(),
    )
}
