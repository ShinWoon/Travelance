package com.moneyminions.presentation.screen.game.teamchooseview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold12
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.theme.pretendard
import com.moneyminions.presentation.viewmodel.game.ChooseTeamGameViewModel

@Composable
fun TeamChooseStartView(
    modifier: Modifier = Modifier,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TeamCountView(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
        Participants(teamChooseTeamGameViewModel = teamChooseTeamGameViewModel)
        
    }
}

@Composable
fun TeamCountView(
    modifier: Modifier = Modifier,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel
) {
//    val teammateInfoList =
//        LaunchedEffect(true) {
//            teamChooseTeamGameViewModel.getTeammateInfo()
//        }

    var teamCnt by remember { mutableIntStateOf(teamChooseTeamGameViewModel.teamCnt.value) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(PinkDarkest),
            onClick = {
                if (teamCnt == 0) teamCnt = 0
                else teamCnt -= 1
            }) {
            Text(
                text = "-",
                color = White,
                style = CustomTextStyle.pretendardBold16
            )
        }
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = teamCnt.toString(),
            color = DarkerGray,
            style = CustomTextStyle.pretendardSemiBold16
        )
        Spacer(modifier = modifier.width(16.dp))
        Button(
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(PinkDarkest),
            onClick = {
                teamCnt += 1
            }) {
            Text(
                text = "+",
                color = White,
                style = CustomTextStyle.pretendardBold16
            )
        }
    }
}

@Composable
fun Participants(
    modifier: Modifier = Modifier,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel
) {
    var participantsList by remember { mutableIntStateOf(teamChooseTeamGameViewModel.teamCnt.value) }
    Column {
        Row {
            Text(
                text = "참여자",
                color = DarkerGray,
                style = pretendardBold16
            )
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    tint = PinkDarkest,
                    contentDescription = "add participants")
                Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = "참여자 추가하기",
                    color = DarkerGray,
                    style = pretendardBold14)
                
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(participantsList) {index ->
                MinionProfile(48.dp)
            }
        }
    }
}

@Composable
fun PickParticipants(
    modifier: Modifier = Modifier,
    teamChooseTeamGameViewModel: ChooseTeamGameViewModel
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            modifier = modifier.wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column {
                Text(
                    text = "친구들",
                    color = DarkerGray,
                    style = pretendardBold16,
                    )
                Spacer(modifier = modifier.height(8.dp))
                LazyVerticalGrid(columns = GridCells.FixedSize(48.dp)) {
                    items(teamChooseTeamGameViewModel.teammate) {index ->
                        Column {
                            MinionProfile(size = 36.dp)
                            Text(
                                text = index.nickName,
                                color = DarkerGray,
                                style = pretendardSemiBold12,
                                )
                        }
                    }
                }
            }
        }

    }
}