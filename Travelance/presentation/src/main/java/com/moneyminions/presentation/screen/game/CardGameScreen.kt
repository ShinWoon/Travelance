package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moneyminions.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardGameScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_layer_1),
            contentDescription = "first card layer",
        )
        Image(
            painter = painterResource(id = R.drawable.card_layer_2),
            contentDescription = "second card layer",
        )
        Image(
            painter = painterResource(id = R.drawable.card_layer_3),
            contentDescription = "third card layer",
        )
        Image(
            painter = painterResource(id = R.drawable.card_layer_4),
            contentDescription = "fourth card layer",
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CardGamePreview() {
    CardGameScreen(navController = rememberNavController())
}
