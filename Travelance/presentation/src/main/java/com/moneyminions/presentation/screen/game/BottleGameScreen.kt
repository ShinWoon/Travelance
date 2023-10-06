package com.moneyminions.presentation.screen.game

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.Coke
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.viewmodel.game.BottleGameViewModel


private const val TAG = "BottleGameScreen"

@Composable
fun BottleGameScreen(
    navController: NavHostController,
    bottleGameViewModel: BottleGameViewModel = hiltViewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        RuleScreen()
    }
}

@Composable
fun RuleScreen() {

    var isStart by remember {
        mutableStateOf(false)
    }

    var isRestart by remember {
        mutableStateOf(false)
    }

    var rotationValue by remember {
        mutableStateOf(0f)
    }

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 3000,
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            isRestart = !isRestart
        },
        label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .weight(4f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isStart) {
                Image(
                    painter = painterResource(id = R.drawable.ic_coke),
                    contentDescription = "ruleta",
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(angle)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_coke_lid),
                    contentDescription = "start",
                    modifier = Modifier.clickable {
                        isStart = !isStart
                        rotationValue = (4000..5000).random().toFloat() + angle
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 40.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (isRestart) {
                Button(
                    modifier = Modifier,
                    onClick = {
                        isStart = !isStart
                        isRestart = !isRestart
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Coke)
                ) {
                    Text(text = "다시하기", style = pretendardBold20, color = White)
                }
            }
        }
    }
}




