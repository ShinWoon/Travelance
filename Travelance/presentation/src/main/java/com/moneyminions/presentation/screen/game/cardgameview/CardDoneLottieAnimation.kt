package com.moneyminions.presentation.screen.game.cardgameview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader

@Composable
fun CardDoneLottieAnimation() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieLoader(res = R.raw.cardgame_confetti, modifier = Modifier.size(400.dp)){}
    }
}