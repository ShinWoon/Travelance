package com.moneyminions.presentation.screen.game.cardgameview

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.moneyminions.domain.model.home.TravelRoomFriendDto

@Composable
fun CardSlider(
    modifier: Modifier = Modifier,
    slideInDurationMillis: Int = 700,
    slideInDelayMillis: Int = 700,
    onSlideComplete: () -> Unit,
    shakeComplete: MutableState<Boolean>,
    dialogShow: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val cardNumbers = listOf(1, 2, 3, 4)

        cardNumbers.forEachIndexed { index, cardNumber ->
            val slideInAnimatable = remember { Animatable(-(10f - index)) }

            LaunchedEffect(index) {
                slideInAnimatable.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = slideInDurationMillis,
                        delayMillis = index * slideInDelayMillis,
                        easing = FastOutSlowInEasing,
                    ),
                )

                if (index == cardNumbers.size - 1) {
                    onSlideComplete()
                }
            }

            val offset = slideInAnimatable.value

            when (cardNumber) {
                1 -> CardOneView(offset = offset)
                2 -> CardTwoView(offset = offset)
                3 -> CardThreeView(offset = offset)
                4 -> CardFourView(offset = offset, shakeComplete = shakeComplete, dialogShow = dialogShow)
                else -> { /* 처리할 로직 추가 */ }
            }
        }
    }
}
