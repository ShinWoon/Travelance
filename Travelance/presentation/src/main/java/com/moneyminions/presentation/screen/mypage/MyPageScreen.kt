package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.MyPageAccountListComponent
import com.moneyminions.presentation.screen.mypage.view.MyPageCardListComponent
import com.moneyminions.presentation.screen.mypage.view.MyPageTopComponent
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.mypage.MyPageViewModel

private const val TAG = "MyPageScreen D210"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageScreeen(
    navController: NavHostController,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {

    val memberInfoResultState by myPageViewModel.memberInfoResult.collectAsState()
    NetworkResultHandler(
        state = memberInfoResultState,
        errorAction = {
            Log.d(TAG, "MemberInfo 조회 오류")
        },
        successAction = {
            myPageViewModel.setCardList(it.cardList)
            myPageViewModel.setAccountList(it.accountList)
            myPageViewModel.setNickname(it.nickname)
        }
    )
    LaunchedEffect(
        key1 = Unit,
        block = {
            myPageViewModel.getMemberInfo()
        }
    )
    val nicknameState = myPageViewModel.nickname.collectAsState()

    val accountListState = myPageViewModel.accountList.collectAsState()
    val accountListPagerState = rememberPagerState(pageCount = {
        accountListState.value.size
    })
    var accountListSelectedIdx by remember { mutableStateOf(0) }

    val cardListState = myPageViewModel.cardList.collectAsState()
    val cardListPagerState = rememberPagerState(pageCount = {
        cardListState.value.size
    })
    var cardListSelectedIdx by remember { mutableStateOf(0) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        MyPageTopComponent(
            nickname = nicknameState.value
        ) {
            navController.navigate(Screen.Setting.route)
        }
        Spacer(modifier = Modifier.size(16.dp))
        MyPageAccountListComponent(
            pagerState = accountListPagerState,
            accountList = accountListState.value
        )
        Spacer(modifier = Modifier.size(16.dp))
        MyPageCardListComponent(
            pagerState = cardListPagerState,
            cardList = cardListState.value
        )
        LaunchedEffect(cardListPagerState.currentPage) {
            cardListSelectedIdx = cardListPagerState.currentPage
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MyPageScreenPreview() {
    MyPageScreeen(navController = rememberNavController())
}
