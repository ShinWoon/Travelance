package com.moneyminions.presentation.screen.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.screen.game.teamchooseview.TeamChooseResultView
import com.moneyminions.presentation.screen.game.teamchooseview.TeamChooseStartView
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBuildingGameScreen(
    navController: NavHostController,
    chooseTeamGameViewModel: ChooseTeamGameViewModel = hiltViewModel(),
) {
//        TeamChooseStartView(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
//    delay()
        TeamChooseResultView(count = 4, chooseTeamGameViewModel = chooseTeamGameViewModel)
}
