package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.common.TopBar
import kotlinx.coroutines.delay


private const val TAG = "BottleGameScreen"

@Composable
fun BottleGameScreen(
    navController: NavHostController,
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
                RouletteScreen()
            }
        }
    }
}


@Composable
fun RouletteWheel(rotationAngle: Float) {
    val wheelRadius = 100.dp
    val center = Offset(wheelRadius.value, wheelRadius.value)
    val numSections = 6 // 변경 가능한 섹션 수
    
    Canvas(
        modifier = Modifier.size(wheelRadius * 2f),
        onDraw = {
            for (i in 0 until numSections) {
                val startAngle = i * 360f / numSections + rotationAngle
                val sweepAngle = 360f / numSections
                val color = if (i % 2 == 0) Color.Blue else Color.Red
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(0f, 0f),
                    size = Size(wheelRadius.toPx() * 2, wheelRadius.toPx() * 2)
                )
            }
            
            // 원판 가운데 십자 모양 그리기
//            drawLine(
//                color = Color.White,
//                start = Offset(center.x - 10f, center.y),
//                end = Offset(center.x + 10f, center.y),
//                strokeWidth = 5f
//            )
//            drawLine(
//                color = Color.White,
//                start = Offset(center.x, center.y - 10f),
//                end = Offset(center.x, center.y + 10f),
//                strokeWidth = 5f
//            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RouletteScreen() {
    var selectedPersonCount by remember { mutableStateOf(1) }
    var isSpinning by remember { mutableStateOf(false) }
    var rotationAngle by remember { mutableStateOf(0f) }
    
    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            delay(500) // 4초 대기
            isSpinning = false
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = selectedPersonCount.toString(),
            onValueChange = {
                val newCount = it.toIntOrNull() ?: 1
                selectedPersonCount = if (newCount < 1) 1 else newCount
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
            keyboardActions = KeyboardActions(
                onDone = {
                    // 키보드의 완료 버튼을 눌렀을 때 포커스 해제
//                    LocalSoftwareKeyboardController.current?.hide()
                }
            ),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        RouletteWheel(rotationAngle = rotationAngle)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!isSpinning) {
                    // 4초 동안 회전
                    val rotations = (4 * 1000).toFloat() / 360f
                    rotationAngle += 360 * rotations
                    isSpinning = true
                }
            }
        ) {
            Text(text = "돌림판 돌리기")
        }
    }
}
