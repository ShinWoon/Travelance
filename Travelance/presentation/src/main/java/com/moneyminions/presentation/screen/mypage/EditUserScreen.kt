package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
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

    val deleteCardResultState by editUserViewModel.deleteCardResult.collectAsState()
    NetworkResultHandler(
        state = deleteCardResultState,
        errorAction = {
            Log.d(TAG, "delete Card error... ")
        },
        successAction = {
            editUserViewModel.getMemberInfo()
        }
    )

    val deleteAccountResultState by editUserViewModel.deleteAccountResult.collectAsState()
    NetworkResultHandler(
        state = deleteAccountResultState,
        errorAction = {
            Log.d(TAG, "delete Account error....")
        },
        successAction = {
            editUserViewModel.getMemberInfo()
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                onDelete = { bankName, accountNumber ->
                    editUserViewModel.setDeleteAccountInfo(bankName, accountNumber)
                    editUserViewModel.setIsAccountDeleteDialogShow(true)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            CardListComponent(
                cardList = cardListState.value,
                onDelete = { cardName, cardNumber ->
                    editUserViewModel.setDeleteCardInfo(cardName, cardNumber)
                    editUserViewModel.setIsCardDeleteDialogShow(true)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        MinionPrimaryButton(
            content = "마이데이터 자산 추가하기",
            modifier = Modifier.fillMaxWidth()
        ) {
            navController.navigate(Screen.AccountAuthentication.route)
        }
        if(isAccountDeleteDialogShowState.value){
            SimpleDeleteDialog(
                onDismiss = {
                    editUserViewModel.setIsAccountDeleteDialogShow(false)
                },
                onConfirm = {
                    //계좌 삭제 api 호출
                    editUserViewModel.deleteAccount()
                    editUserViewModel.setIsAccountDeleteDialogShow(false)
                }
            )

        }
        if(isCardDeleteDialogShowState.value){
            SimpleDeleteDialog(
                onDismiss = {
                    editUserViewModel.setIsCardDeleteDialogShow(false)
                },
                onConfirm = {
                    editUserViewModel.deleteCard()
                    editUserViewModel.setIsCardDeleteDialogShow(false)
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
