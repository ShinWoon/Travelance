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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.result.view.PaymentInfoComponenet
import com.moneyminions.presentation.screen.result.view.SettleResultCardView
import com.moneyminions.presentation.screen.result.view.UserPaymentInfoComponent
import com.moneyminions.presentation.theme.LightGray
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.result.SettleResultReceiveViewModel
import kotlinx.coroutines.launch

private const val TAG = "SettleResultReceiveScre D210"
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettleResultReceiveScreen(
    navController: NavHostController,
    settleResultReceiveViewModel: SettleResultReceiveViewModel = hiltViewModel(),
    roomId: Int
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(
        key1 = Unit,
        block = {
            settleResultReceiveViewModel.getSettleResult(roomId)
        }
    )

    val settleResultState by settleResultReceiveViewModel.settleResult.collectAsState()
    NetworkResultHandler(
        state = settleResultState,
        errorAction = {
            Log.d(TAG, "MemberInfo 조회 오류")
        },
        successAction = {
            Log.d(TAG, "SettleResultReceiveScreen result : $it")
            settleResultReceiveViewModel.setSettleResultDto(it)
        }
    )

    val postFinalPaymentResultState by settleResultReceiveViewModel.finalPaymentResult.collectAsState()
    NetworkResultHandler(
        state = postFinalPaymentResultState,
        errorAction = {
            Log.d(TAG, "이체 실패")
            coroutineScope.launch {
                snackbarHostState.showSnackbar("계좌 잔액 또는 비밀번호를 확인하시오!")
            }
        },
        successAction = {
            navController.navigate(Screen.Home.route){
                popUpTo(Screen.SettleResult.route){inclusive = true}
            }
        }
    )

    val isShowDialogState = settleResultReceiveViewModel.isShowDialog.collectAsState()

//    Scaffold(
//        topBar = {
//            TopBar(navController = navController, topBarTitle = "정산결과")
//        }
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween,
//        ) {
//            Column {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    verticalAlignment = Alignment.Bottom,
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_calender),
//                        contentDescription = "detail calender icon",
//                        modifier = Modifier.size(40.dp),
//                    )
//                    DetailDateView(
//                        startDate = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.startDate ?: "",
//                        endDate = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.endDate ?: "",
//                        modifier = Modifier
//                    )
//                    BudgetText(budget = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.budget ?: 0, modifier = Modifier)
//                }
//                Spacer(modifier = Modifier.size(16.dp))
//                Column(
//                    modifier = Modifier.background(LightGray)
//                ) {
//                    PaymentInfoComponenet(settleResultPaymentInfoDto = settleResultReceiveViewModel.settleResultDto.value?.paymentInfo ?: null )
//                    Spacer(modifier = Modifier.size(16.dp))
////                    SettleResultCardView(result = result, modifier = Modifier)
//                    //내가 돈을 더 많이 써서 받아야 한다면
//                    if ((settleResultReceiveViewModel.settleResultDto.value?.receiveInfos?.size?:0) >0) {
//                        UserPaymentInfoComponent(receiveInfos = settleResultReceiveViewModel.settleResultDto.value?.receiveInfos, sendInfos = null)
//                    } else if((settleResultReceiveViewModel.settleResultDto.value?.sendInfos?.size?:0) >0){ //내가 이체해야 한다면
//                        UserPaymentInfoComponent(receiveInfos = null, sendInfos = settleResultReceiveViewModel.settleResultDto.value?.sendInfos)
//                    }
//                }
//            }
//            //내가 돈을 더 많이 써서 받아야 한다면
//            if ((settleResultReceiveViewModel.settleResultDto.value?.paymentInfo?.transferTotalAmount?:0) <=0) {
//                MinionPrimaryButton(content = "확인", modifier = Modifier.fillMaxWidth()) {
//                    navController.navigate(Screen.Home.route){
//                        popUpTo(Screen.SettleResult.route){inclusive = true}
//                    }
//                }
//            } else { //내가 이체해야 한다면
//                MinionPrimaryButton(content = "이체", modifier = Modifier.fillMaxWidth()) {
//                    settleResultReceiveViewModel.setIsShowDialog(true)
//                }
//            }
//        }
//    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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
                    startDate = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.startDate ?: "",
                    endDate = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.endDate ?: "",
                    modifier = Modifier
                )
                BudgetText(budget = settleResultReceiveViewModel.settleResultDto.value?.travelRoomInfo?.budget ?: 0, modifier = Modifier)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.background(LightGray)
            ) {
                PaymentInfoComponenet(settleResultPaymentInfoDto = settleResultReceiveViewModel.settleResultDto.value?.paymentInfo ?: null )
                Spacer(modifier = Modifier.size(16.dp))
//                    SettleResultCardView(result = result, modifier = Modifier)
                Log.d(TAG, "SettleResultReceiveScreen receiveInfos.size : ${settleResultReceiveViewModel.settleResultDto.value?.receiveInfos?.size?: -1}")
                Log.d(TAG, "SettleResultReceiveScreen sendInfos.size : ${settleResultReceiveViewModel.settleResultDto.value?.sendInfos?.size?: -1}")
                //내가 돈을 더 많이 써서 받아야 한다면
                if ((settleResultReceiveViewModel.settleResultDto.value?.receiveInfos?.size?:0) >0) {
                    UserPaymentInfoComponent(receiveInfos = settleResultReceiveViewModel.settleResultDto.value?.receiveInfos, sendInfos = null)
                } else if((settleResultReceiveViewModel.settleResultDto.value?.sendInfos?.size?:0) >0){ //내가 이체해야 한다면
                    UserPaymentInfoComponent(receiveInfos = null, sendInfos = settleResultReceiveViewModel.settleResultDto.value?.sendInfos)
                }
            }
        }
        //내가 돈을 더 많이 써서 받아야 한다면
        if ((settleResultReceiveViewModel.settleResultDto.value?.paymentInfo?.transferTotalAmount?:0) <=0) {
            MinionPrimaryButton(content = "확인", modifier = Modifier.fillMaxWidth()) {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.SettleResult.route){inclusive = true}
                }
            }
        } else { //내가 이체해야 한다면
            MinionPrimaryButton(content = "이체", modifier = Modifier.fillMaxWidth()) {
                settleResultReceiveViewModel.setIsShowDialog(true)
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
            settleResultReceiveViewModel.setFinalPaymentDto(roomId)
            settleResultReceiveViewModel.postFinalPayment()
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SettleResultReceiveScreenPreview(){
    SettleResultReceiveScreen(navController = rememberNavController(), roomId = 0)
}
