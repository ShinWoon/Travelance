package com.moneyminions.presentation.screen.game.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.PinkLightest
import com.moneyminions.presentation.viewmodel.game.WordGameViewModel

@Composable
fun WordComponent(
    wordGameViewModel: WordGameViewModel = hiltViewModel()
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(128.dp) // 원하는 크기 설정
                .background(PinkLightest, shape = RoundedCornerShape(16.dp))
        ){
            Text(
                text = wordGameViewModel.firstConsonant.value,
                style = CustomTextStyle.pretendardExtraBold64,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Box(
            modifier = Modifier
                .size(128.dp) // 원하는 크기 설정
                .background(PinkLightest, shape = RoundedCornerShape(16.dp))
        ){
            Text(
                text = wordGameViewModel.secondConsonant.value,
                style = CustomTextStyle.pretendardExtraBold64,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}