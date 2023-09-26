package com.moneyminions.presentation.screen.mypage

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.SimpleDeleteDialog
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.mypage.view.AccountListComponent
import com.moneyminions.presentation.screen.mypage.view.CardListComponent
import com.moneyminions.presentation.screen.mypage.view.EditName
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.mypage.EditUserViewModel

private const val TAG = "EditUserScreen D210"
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditUserScreen(
    navController: NavHostController,
    editUserViewModel: EditUserViewModel = hiltViewModel()
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
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            CardListComponent(cardListState.value)
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
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun EditUserScreenPreview() {
    EditUserScreen(navController = rememberNavController())
}
