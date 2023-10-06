package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.game.view.WordComponent
import com.moneyminions.presentation.viewmodel.game.WordGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordGameScreen(
    navController: NavHostController,
    wordGameViewModel: WordGameViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (wordGameViewModel.isShowWord.value) {
            WordComponent()
        } else {
            if (wordGameViewModel.isStart.value) {
                LottieLoader(
                    modifier = Modifier.size(128.dp),
                    res = R.raw.lottie_count,
                    isLoop = false
                ) {
                    wordGameViewModel.setIsShowWord(true)
                }
            } else {
                LottieLoader(
                    modifier = Modifier.size(256.dp),
                    res = R.raw.lottie_start_wordgame
                ) {}
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        MinionPrimaryButton(
            content = getButtonContent(),
            modifier = Modifier
        ) {
            wordGameViewModel.setIsShowWord(false)
            wordGameViewModel.setIsStart(true)
//                    scope.launch {
//                        delay(3000) // 3초 지연
//                        wordGameViewModel.setIsShowWord(true)
//                    }
            wordGameViewModel.setFirstConsonant()
            wordGameViewModel.setSecondConsonant()
        }
    }
}


@Composable
fun getButtonContent(
    wordGameViewModel: WordGameViewModel = hiltViewModel()
): String {
    return if (wordGameViewModel.firstConsonant.value.isEmpty()) {
        "시작"
    } else {
        "다시"
    }
}

@Preview(showBackground = true)
@Composable
fun WordGameScreenPreview() {
    WordGameScreen(navController = rememberNavController())
}