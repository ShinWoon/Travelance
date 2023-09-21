package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBuildingGameScreen(
    navController: NavHostController,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel = hiltViewModel(),
) {
    val teammateInfoList =
        LaunchedEffect(true) {
            teamChooseTeamGameViewModel.getTeammateInfo()
        }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, title = "팀 정하기")
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
        }
    }
}

@Composable
fun TeamCountView(
    modifier: Modifier = Modifier,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel
) {
    val teamCnt = teamChooseTeamGameViewModel.teamCnt
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { /*TODO*/ }) {

        }
        Spacer(modifier = modifier.width(8.dp))
        Text(text = )
        Spacer(modifier = modifier.width(8.dp))
        Button(onClick = { /*TODO*/ }) {

        }
    }
}
