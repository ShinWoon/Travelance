package com.moneyminions.presentation.screen.game

import android.util.Log
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold24
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.CardLightGray

private const val TAG = "GameListScreen_D210"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    travelId: Int,
) {
    Log.d(TAG, "GameListScreen: $travelId")
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollableState)
    ) {
        GameCard(
            title = "카드 뽑기",
            gameImg = R.drawable.ic_card_game,
            action = {
                navController.navigate("${Screen.CardGame.route}/{roomId}".replace(oldValue = "{roomId}", newValue = "$travelId"))
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        GameCard(
            title = "지목하기",
            gameImg = R.drawable.ic_bottle,
            action = {
                navController.navigate(Screen.BottleGame.route)
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        GameCard(
            title = "팀 정하기",
            gameImg = R.drawable.ic_team_building,
            action = {
                navController.navigate("${Screen.TeamBuildingGame.route}/{roomId}".replace(oldValue = "{roomId}", newValue = "$travelId"))
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        GameCard(
            title = "초성 게임",
            gameImg = R.drawable.ic_word_game,
            action = {
                navController.navigate(Screen.WordGame.route)
            },
        )
    }
}

@Composable
fun GameCard(
    title: String,
    gameImg: Int,
    action: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 8.dp)
            .clickable(
                onClick = action,
            ),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopStart,
            ) {
                Text(text = title, style = pretendardBold24)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Image(
                    painter = painterResource(id = gameImg),
                    contentDescription = "game image",
                )
            }
        }
    }
}
