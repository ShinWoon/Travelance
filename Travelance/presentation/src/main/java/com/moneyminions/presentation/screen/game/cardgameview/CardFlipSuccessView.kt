package com.moneyminions.presentation.screen.game.cardgameview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold32
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.pretendard

private const val TAG = "CardFlipSuccessView_D210"

@Composable
fun CardFlipSuccessView(
    scale: Float,
    selectedWinnerFriend: TravelRoomFriendDto,
    modifier: Modifier = Modifier,
    showDone: Boolean,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(scaleX = scale, scaleY = scale),
            painter = painterResource(id = R.drawable.card_layer_result),
            contentDescription = "result card",
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            MinionProfile(size = 120.dp, img = selectedWinnerFriend.profileUrl)
            Spacer(modifier = modifier.height(32.dp))
            Text(
                text = selectedWinnerFriend.travelNickname,
                color = DarkerGray,
                style = pretendardBold32
            )
        }
        if(showDone) {
            CardDoneLottieAnimation()
        }
    }
}