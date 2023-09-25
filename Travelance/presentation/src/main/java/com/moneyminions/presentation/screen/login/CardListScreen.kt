package com.moneyminions.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.accountList
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.login.CardListViewModel
import kotlinx.coroutines.launch



private const val TAG = "CardListScreen D210"
@Composable
fun CardListScreen(
    navController: NavHostController,
    cardListViewModel: CardListViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
){
    val coroutineScope = rememberCoroutineScope()
    val cardListResultState by cardListViewModel.cardListResult.collectAsState() //계좌 불러오는 api 결과 상태
    val cardListState  = cardListViewModel.cardList.collectAsState() //내가 이 화면에 사용할 내 계좌 리스트의 상태
    NetworkResultHandler(
        state = cardListResultState,
        errorAction = {
            Log.d(TAG, "AccountList error...")
        },
        successAction = {
            coroutineScope.launch {
                Log.d(TAG, "AccountList success : $it ")
                cardListViewModel.setCardList(it)
            }
        }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {cardListViewModel.getCardList()}
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "카드 목록"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn{
                items(cardListState.value){
                    var isSelectedState by remember { mutableStateOf(it.isSelected) }
                    Log.d(TAG, "AccountListScreen: $isSelectedState")
                    CardRowItem(
                        name = it.name,
                        number = it.number,
                        idx = it.idx,
                        type = "select",
                        isSelected = isSelectedState,
                        onSelected = {
                            isSelectedState = !isSelectedState!!
                            it.isSelected = !it.isSelected!!
                            Log.d(TAG, "AccountListScreen: $accountList")
                        }
                    )
                }
            }
            MinionPrimaryButton(
                content = "다음",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                mainViewModel.setMemberCardList(cardListState.value.filter { it.isSelected!! })
                Log.d(TAG, "CardListScreen에서 mainviewmodel의 memberInfo : ${mainViewModel.memberInfo}")
                navController.navigate(Screen.NicknamePassword.route)
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CardListScreenPreview(){
    CardListScreen(navController = rememberNavController())
}