package com.moneyminions.presentation.screen.game.cardgameview

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold32
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController

private const val TAG = "CardViews"

@Composable
fun CardOneView(
    offset: Float,
) {
    Image(
        painter = painterResource(id = R.drawable.card_layer_1),
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(translationX = offset * 200) // 여기에 수정
            .padding(8.dp),
    )
}

@Composable
fun CardTwoView(
    offset: Float,
) {
    Image(
        painter = painterResource(id = R.drawable.card_layer_2),
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(translationX = offset * 200) // 여기에 수정
            .padding(8.dp),
    )
}

@Composable
fun CardThreeView(
    offset: Float,
) {
    Image(
        painter = painterResource(id = R.drawable.card_layer_3),
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(translationX = offset * 200) // 여기에 수정
            .padding(8.dp),
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun CardFourView(
    offset: Float,
    shakeComplete: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    dialogShow: () -> Unit
) {
    val controller = remember(true) { FlippableController() }
    var isCardFlipSuccessVisible by remember { mutableStateOf(false) }

    if (shakeComplete.value) {
        controller.flipToBack()
        Log.d(TAG, "CardFourView: ${shakeComplete.value}")
    }

    Flippable(
        frontSide = {
            Image(
                painter = painterResource(id = R.drawable.card_layer_4),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer(translationX = offset * 200) // 여기에 수정
                    .padding(8.dp),
            )
        },
        backSide = {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card_layer_4),
                    contentDescription = null,
                    modifier = modifier.graphicsLayer(translationX = offset * 200) // 여기에 수정
                        .padding(8.dp),
                )
//
//                Column(
//                    modifier = modifier
//                        .graphicsLayer(translationX = offset * 200) // 여기에 수정
//                        .padding(8.dp)
//                        .rotate(-18.00f),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    MinionProfile(size = 80.dp)
//                    Spacer(modifier = modifier.height(64.dp))
//                    Text(
//                        text = "하동혁",
//                        color = DarkerGray,
//                        style = pretendardBold32,
//                    )
//                }
            }
        },
        flipController = controller,
        flipEnabled = true,
        flipOnTouch = false,
        // If true, the Flippable will automatically flip back after
        // duration defined in autoFlipDurationMs. By default, this is false..
//        autoFlip = true,
//
//        // The duration in Milliseconds after which auto-flip back animation will start. Default is 1 second.
//        autoFlipDurationMs = 5000,
        // The animation type of flipping effect. Currently there are 4 animations.
        // Horizontal Clockwise and Anti-Clockwise, Vertical Clockwise and Anti-Clockwise
        // See animation types section below.
        flipAnimationType = FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE,
        onFlippedListener = {
            Log.d(TAG, "CardFourView: 실행 완료")
            isCardFlipSuccessVisible = true
        },
    )
    if (isCardFlipSuccessVisible) {
        dialogShow()
    }
}
