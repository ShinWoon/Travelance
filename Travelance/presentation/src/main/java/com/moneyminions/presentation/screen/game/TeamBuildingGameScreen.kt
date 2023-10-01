package com.moneyminions.presentation.screen.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.screen.game.teamchooseview.TeamChooseStartView
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@Composable
fun TeamBuildingGameScreen(
    navController: NavHostController,
    travelId: Int,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel = hiltViewModel(),
) {
    TeamChooseStartView(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
}
