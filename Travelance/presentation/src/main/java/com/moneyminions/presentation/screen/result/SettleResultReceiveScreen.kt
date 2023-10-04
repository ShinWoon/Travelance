package com.moneyminions.presentation.screen.result

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.AuthenticationDialog
import com.moneyminions.presentation.common.BudgetText
import com.moneyminions.presentation.common.DetailDateView
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.TravelInfoView
import com.moneyminions.presentation.screen.result.view.SettleResultCardView
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.result.SettleResultReeiveViewModel

private const val TAG = "SettleResultReceiveScre D210"
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettleResultReceiveScreen(
    navController: NavHostController,
    settleResultReceiveViewModel: SettleResultReeiveViewModel = hiltViewModel(),
    roomId: Int
) {
    LaunchedEffect(
        key1 = Unit,
        block = {
            settleResultReceiveViewModel.getSettleResult(roomId)
        }
    )
    val result = "send"

    val settleResultState by settleResultReceiveViewModel.settleResult.collectAsState()
    NetworkResultHandler(
        state = settleResultState,
        errorAction = {
            Log.d(TAG, "MemberInfo 조회 오류")
        },
        successAction = {
            Log.d(TAG, "SettleResultReceiveScreen result : $it")
        }
    )


    val isShowDialogState = settleResultReceiveViewModel.isShowDialog.collectAsState()

    Scaffold(
        topBar = {
            TopBar(navController = navController, topBarTitle = "정산결과")
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calender),
                        contentDescription = "detail calender icon",
                        modifier = Modifier.size(40.dp),
                    )
                    DetailDateView(
                        startDate = "2023/10/04",
                        endDate = "2023/10/15",
                        modifier = Modifier
                    )
                    BudgetText(budget = 100000, modifier = Modifier)
                }
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.background(LightGray)
                ) {
//                    SettleResultCardView(result = result, modifier = Modifier)
                }
            }
            if (result == "get") {
                MinionPrimaryButton(content = "확인", modifier = Modifier.fillMaxWidth()) {
                }
            } else {
                MinionPrimaryButton(content = "이체", modifier = Modifier.fillMaxWidth()) {
                    settleResultReceiveViewModel.setIsShowDialog(true)
                }
            }
        }
    }
    AuthenticationDialog(
        navController = navController,
        showDialog = isShowDialogState.value,
        onDismiss = {
            settleResultReceiveViewModel.setIsShowDialog(false)
            Log.d(TAG, "취소 클릭 : ${settleResultReceiveViewModel.isShowDialog.value}")
        },
        type = "password",
        value = settleResultReceiveViewModel.password.value,
        onValueChange = {
            settleResultReceiveViewModel.setPassword(it)
        },
        onConfirm = {
            //여기서 비밀번호 담아서 api 호출 (이체하는)
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SettleResultReceiveScreenPreview(){
    SettleResultReceiveScreen(navController = rememberNavController(), roomId = 0)
}
