package com.moneyminions.presentation.screen.game.cardgameview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader

@Composable
fun CardFlipSuccessView(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LottieLoader(
            lottieFile = R.raw.cardgame_confetti,
            iteration = 3000,
            modifier = modifier.fillMaxSize(),
        )
    }
}
