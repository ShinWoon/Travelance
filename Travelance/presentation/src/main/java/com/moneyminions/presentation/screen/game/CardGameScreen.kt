package com.moneyminions.presentation.screen.game

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.presentation.screen.game.cardgameview.CardGameStartView
import com.moneyminions.presentation.screen.game.cardgameview.CardSlider
import com.moneyminions.presentation.screen.game.cardgameview.ShakeDetector
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.game.CardGameViewModel

private const val TAG = "CardGameScreen"

@Composable
fun CardGameScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    travelId: Int,
    cardGameViewModel: CardGameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cardSliderComplete = remember { mutableStateOf(false) }
    val shakeDetector = remember { ShakeDetector(context) }
    val shakeComplete = remember { mutableStateOf(false) }
    val flipComplete = remember { mutableStateOf(false) }

    val friendsListState by cardGameViewModel.getTravelRoomFriendState.collectAsState()
    val friendsList by remember {
        mutableStateOf(listOf(TravelRoomFriendDto()))
    }
    var selectedWinnerFriend by remember { mutableStateOf(TravelRoomFriendDto()) }

    NetworkResultHandler(state = friendsListState, errorAction = { /*TODO*/ }, successAction = {
        selectedWinnerFriend = cardGameViewModel.getWinner(friendsList)
    })
    
    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "CardGameScreen: $travelId")
        cardGameViewModel.getTravelRoomFriends(travelId)
    }

    // CardSlider 애니메이션 완료 시 콜백
    val onSlideComplete: () -> Unit = {
        cardSliderComplete.value = true
        // 흔들림 감지 시작
        shakeDetector.startListening {
            // 실제 흔들림 감지 시 원하는 동작 수행
            Log.d(TAG, "CardGameScreen: Shaked")
            shakeComplete.value = true
            // 흔들림 감지 후 감지 중지
            shakeDetector.stopListening()
        }
    }

    CardSlider(
        modifier = modifier,
        onSlideComplete = onSlideComplete,
        shakeComplete = shakeComplete,
    )

    // cardSliderComplete 값에 따라 CardGameStartView를 렌더링 여부를 결정
    if (cardSliderComplete.value && !shakeComplete.value) {
        CardGameStartView(modifier = modifier, isVisible = true)
    }
}

@Preview(showBackground = true)
@Composable
fun CardGamePreview() {
//    CardGameScreen(navController = rememberNavController())
}
