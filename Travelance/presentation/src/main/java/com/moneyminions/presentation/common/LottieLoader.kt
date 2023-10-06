package com.moneyminions.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

private const val TAG = "LottieLoader_D210"
@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    res: Int,
    startTime: Float = 0f,
    endTime: Float = 1f,
    isLoop: Boolean = true,
    onFinished: () -> Unit
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(res))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        restartOnPlay = false,
        clipSpec = LottieClipSpec.Progress(startTime, endTime),
        iterations = if(isLoop) LottieConstants.IterateForever else 1
    )
    if(progress == 1f){
        onFinished()
    }
    LottieAnimation(
        composition,
        progress = { progress },
        modifier = modifier
    )
}
