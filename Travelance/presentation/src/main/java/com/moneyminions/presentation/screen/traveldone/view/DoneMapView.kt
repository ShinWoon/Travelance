package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.theme.BlueDarkest

@Composable
fun DoneMapView(
    modifier: Modifier = Modifier,
    showMap: @Composable () -> Unit,
    moveToMap: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = { moveToMap() },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "여행의 발자취 확인해 보러가봐요!",
                    color = BlueDarkest,
                    style = pretendardSemiBold14,
                )
            }
            Spacer(modifier = modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_map_point),
                modifier = modifier.size(24.dp),
                contentDescription = "marker icon",
            )
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(360.dp),
        ) {
            showMap()
        }
    }
}
