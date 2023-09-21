package com.moneyminions.presentation.screen.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.screen.game.teamchooseview.TeamChooseStartView
import com.moneyminions.presentation.screen.game.teamchooseview.TeamCountView
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBuildingGameScreen(
    navController: NavHostController,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel = hiltViewModel(),
) {
    TeamChooseStartView(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
}