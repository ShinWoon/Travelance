package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.LottieLoader
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@Composable
fun TeamBuildingGameScreen(
    navController: NavHostController,
    travelId: Int,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel = hiltViewModel(),
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LottieLoader(res = R.raw.lottie_comming_soon, modifier = Modifier.size(400.dp)){}
    }
//    TeamChooseStartView(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
}
