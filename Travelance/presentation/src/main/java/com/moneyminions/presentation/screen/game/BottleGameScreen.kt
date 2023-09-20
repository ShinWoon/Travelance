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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.viewmodel.game.BottleGameViewModel


private const val TAG = "BottleGameScreen"

@Composable
fun BottleGameScreen(
    navController: NavHostController,
    bottleGameViewModel: BottleGameViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, title = "지목하기")
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
            ) {
                RuleScreen()
            }
        }
    }
}

@Composable
fun RuleScreen() {
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
        },
        label = ""
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_finger),
                contentDescription = "ruleta",
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(angle)
            )
        }
        
        Box(
            modifier = Modifier
                .padding(horizontal = 30.dp)
        )
        {
            MinionPrimaryButton(
                content = "Start",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                rotationValue = (4000..5000).random().toFloat() + angle
            }
        }
    }
}




