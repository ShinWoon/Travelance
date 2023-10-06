package com.moneyminions.presentation.screen.game.cardgameview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.Black
import com.moneyminions.presentation.theme.DarkerGray

@Composable
fun CardGameStartView(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
) {
    if (isVisible) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Black.copy(alpha = 0.2f)),
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = modifier.size(360.dp),
                    painter = painterResource(id = R.drawable.phone_holding),
                    contentDescription = "card game start image",
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = "핸드폰을 흔들어 카드를 뽑아 봐요!",
                    color = DarkerGray,
                    style = CustomTextStyle.pretendardBold20,
                )
            }
        }
    }
}
