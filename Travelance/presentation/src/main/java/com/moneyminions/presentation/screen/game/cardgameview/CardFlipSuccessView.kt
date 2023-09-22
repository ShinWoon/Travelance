package com.moneyminions.presentation.screen.game.cardgameview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader
import com.moneyminions.presentation.theme.PinkDarkest

private const val TAG = "CardFlipSuccessView_D210"
@Composable
fun CardFlipSuccessView(
    modifier: Modifier = Modifier,
) {
    var isResultDialog by remember { mutableStateOf(false) }
    
    LottieLoader(
        modifier = Modifier,
        res = R.raw.lottie_star,
        startTime = 0f,
        endTime = 1f,
        isLoop = false
    ) {
        isResultDialog = true
    }
    
    Log.d(TAG, "CardFlipSuccessView: ${isResultDialog}")
    
    if (isResultDialog) {
        ResultDialog(
            onDismiss = { isResultDialog = false }
        )
    }
}


@Composable
fun ResultDialog(
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Box (
                modifier = Modifier
                    .background(PinkDarkest)
                    .height(300.dp)
                    .width(200.dp)
            ){
                // 당첨자 데이터 넣기
            }
        }
    }
}
