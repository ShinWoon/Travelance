package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.common.CustomTextStyle.pretendardLight08
import com.moneyminions.presentation.theme.CardLightGray


@Composable
fun BottomCardContainer() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            BottomCardItem(
                modifier = Modifier.padding(4.dp).weight(1f),
                title = "수기 입력",
                context = "여행 준비 내역과 현금 지물 내역을 \n입력해 봐요",
                icon = painterResource(id = R.drawable.ic_money)
            )
            BottomCardItem(
                modifier = Modifier.padding(4.dp).weight(1f),
                title = "필독",
                context = "친구들에게 알리고 싶은 내용을 \n입력하고 확인해 봐요",
                icon = painterResource(id = R.drawable.ic_speaker)
            )
        }
    
        Row {
            BottomCardItem(
                modifier = Modifier.padding(4.dp).weight(1f),
                title = "발자취",
                context = "우리의 여행 발자취를 \n확인해 봐요.",
                icon = painterResource(id = R.drawable.ic_map_point)
            )
            BottomCardItem(
                modifier = Modifier.padding(4.dp).weight(1f),
                title = "게임",
                context = "친구들과 함께 게임을 즐겨봐요.",
                icon = painterResource(id = R.drawable.ic_game)
            )
        }
    }
}

@Composable
fun BottomCardItem(
    modifier: Modifier,
    title: String,
    context: String,
    icon: Painter,
) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, style = pretendardBold12)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = context, style = pretendardLight08)
            }
            Image(
                modifier = Modifier.size(50.dp),
                painter = icon,
                contentDescription = "icon"
            )
        }
    }
}