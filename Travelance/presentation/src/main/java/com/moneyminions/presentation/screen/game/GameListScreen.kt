package com.moneyminions.presentation.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold24
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(
    navController: NavHostController
) {
    val scrollableState = rememberScrollState()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, title = "게임")
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollableState)
        ) {
            Column {
                GameCard(
                    title = "카드 뽑기",
                    gameImg = R.drawable.ic_card_game,
                    action = {
                        navController.navigate(Screen.CardGame.route)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GameCard(
                    title = "지목하기",
                    gameImg = R.drawable.ic_bottle,
                    action = {
                        navController.navigate(Screen.BottleGame.route)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GameCard(
                    title ="팀 정하기",
                    gameImg = R.drawable.ic_team_building,
                    action = {
                        navController.navigate(Screen.TeamBuildingGame.route)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GameCard(
                    title = "초성 게임",
                    gameImg = R.drawable.ic_word_game,
                    action = {
                        navController.navigate(Screen.WordGame.route)
                    }
                )
            }
            
        }
    }
}


@Composable
fun GameCard(
    title: String,
    gameImg: Int,
    action: () -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(
                onClick = action
            ),
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box (
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopStart
            ){
                Text(text = title, style = pretendardBold24)
            }
    
            Box (
                modifier = Modifier.weight(1f).padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ){
                Image(
                    painter = painterResource(id = gameImg),
                    contentDescription = "game image"
                )
            }
        }
    }
}