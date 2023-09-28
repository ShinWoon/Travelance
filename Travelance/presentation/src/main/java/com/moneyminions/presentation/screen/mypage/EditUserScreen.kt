package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.SimpleDeleteDialog
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.mypage.view.AccountListComponent
import com.moneyminions.presentation.screen.mypage.view.CardListComponent
import com.moneyminions.presentation.screen.mypage.view.EditName
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.mypage.EditUserViewModel

private const val TAG = "EditUserScreen D210"
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditUserScreen(
    navController: NavHostController,
    editUserViewModel: EditUserViewModel
) {
    val memberInfoResultState by editUserViewModel.memberInfoResult.collectAsState()
    NetworkResultHandler(
        state = memberInfoResultState,
        errorAction = {
            Log.d(TAG, "MemberInfo 조회 오류")
        },
        successAction = {
            editUserViewModel.setCardList(it.cardList)
            editUserViewModel.setAccountList(it.accountList)
            editUserViewModel.setNickname(it.nickname)
        }
    )
    LaunchedEffect(
        key1 = Unit,
        block = {
            editUserViewModel.getMemberInfo()
        }
    )

    val nicknameState = editUserViewModel.nickname.collectAsState()
    val updateNicknameResultState by editUserViewModel.updateNicknameResult.collectAsState()
    NetworkResultHandler(
        state = updateNicknameResultState,
        errorAction = {
            Log.d(TAG, "update nickname error....")
        },
        successAction = {
            editUserViewModel.updateNickname()
        }
    )

    val accountListState = editUserViewModel.accountList.collectAsState()
    val cardListState = editUserViewModel.cardList.collectAsState()

    val isAccountDeleteDialogShowState = editUserViewModel.isAccountDeleteDialogShow.collectAsState()
    val isCardDeleteDialogShowState = editUserViewModel.isCardDeleteDialogShow.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            navController = navController,
            title = "회원 정보 수정",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            EditName(
                value = nicknameState.value,
                onValueChange = {
                    editUserViewModel.setNickname(it)
                },
                onClick = { // 완료 버튼 눌렀을 때
                },
            )
            Spacer(modifier = Modifier.size(16.dp))
            AccountListComponent(
                accountList = accountListState.value,
                onDelete = {
                    editUserViewModel.setIsAccountDeleteDialogShow(true)
                },
                onPlus = {
                    navController.navigate(Screen.AccountAuthentication.route)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            CardListComponent(
                cardList = cardListState.value,
                onDelete = {
                    editUserViewModel.setIsCardDeleteDialogShow(true)
                },
                onPlus = {
                    navController.navigate(Screen.AccountAuthentication.route)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            MinionPrimaryButton(
                content = "마이데이터 자산 추가하기",
                modifier = Modifier
            ) {
                navController.navigate(Screen.AccountAuthentication.route)
            }
        }
        if(isAccountDeleteDialogShowState.value){
            SimpleDeleteDialog(
                onDismiss = {
                    editUserViewModel.setIsAccountDeleteDialogShow(false)
                },
                onConfirm = {
                    //계좌 삭제 api 호출
                }
            )

        }
        if(isCardDeleteDialogShowState.value){
            SimpleDeleteDialog(
                onDismiss = {
                    editUserViewModel.setIsCardDeleteDialogShow(false)
                },
                onConfirm = {
                    //계좌 삭제 api 호출
                }
            )

        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun EditUserScreenPreview() {
    EditUserScreen(navController = rememberNavController(), editUserViewModel = hiltViewModel())
}
