package com.moneyminions.presentation.screen.home.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardLight10
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.CardLightGray

private const val TAG = "BottomComponent"
@Composable
fun BottomCardContainer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomItem(
                modifier = Modifier.weight(1f),
                title = "수기 입력",
                context = "여행 준비 내역과 현금 지물 내역을 입력해 봐요",
                icon = painterResource(id = R.drawable.ic_money),
                action = {
                    Log.d(TAG, "BottomCardContainer: clicked")
                    
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            BottomItem(
                modifier = Modifier.weight(1f),
                title = "필독",
                context = "친구들에게 알리고 싶은 내용을 입력하고 확인해 봐요",
                icon = painterResource(id = R.drawable.ic_speaker),
                action = {
                    Log.d(TAG, "BottomCardContainer: clicked")
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomItem(
                modifier = Modifier.weight(1f),
                title = "발자취",
                context = "우리의 여행 발자취를 확인해 봐요.",
                icon = painterResource(id = R.drawable.ic_map_point),
                action = {
                    Log.d(TAG, "BottomCardContainer: clicked")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            BottomItem(
                modifier = Modifier.weight(1f),
                title = "게임",
                context = "친구들과 함께 게임을 즐겨봐요.",
                icon = painterResource(id = R.drawable.ic_game),
                action = {
                    Log.d(TAG, "BottomCardContainer: clicked")
                }
            )
        }
    }
}

@Composable
fun BottomItem(
    modifier: Modifier,
    title: String,
    context: String,
    icon: Painter,
    action : () -> Unit
) {
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardLightGray)
            .padding(8.dp)
            .clickable(
                onClick = action
            ),
        contentAlignment = Alignment.TopStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = title,
                    style = pretendardBold14,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = context,
                    style = pretendardLight10,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    Image(
                        modifier = Modifier.size(50.dp),
                        painter = icon,
                        contentDescription = "icon",
                    )
                }
            }
        }
    }
}
