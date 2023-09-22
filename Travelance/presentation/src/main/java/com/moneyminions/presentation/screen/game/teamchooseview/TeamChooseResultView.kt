package com.moneyminions.presentation.screen.game.teamchooseview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.theme.CardBlue
import com.moneyminions.presentation.theme.CardGreen
import com.moneyminions.presentation.theme.CardOrange
import com.moneyminions.presentation.theme.CardPurple
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@Composable
fun TeamChooseResultView(
    count: Int,
    chooseTeamGameViewModel: ChooseTeamGameViewModel,
) {
    LaunchedEffect(Unit) {
        chooseTeamGameViewModel.setVisibility(count)
    }
    val colorList = listOf(PinkDarkest, CardBlue, CardGreen, CardOrange, CardPurple)
//    val visibilityChangeList by remember { mutableListOf(chooseTeamGameViewModel.visibleItems) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp,
        ),
        content = {
            items(12) { index ->
                TeamCard(name = "test", color = colorList[index / count])
            }
        },
    )
}
