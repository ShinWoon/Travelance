package com.moneyminions.presentation.screen.handwriting

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.common.MinionButtonSet
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.handwriting.HandWritingViewModel
import com.moneyminions.presentation.viewmodel.home.HomeViewModel


private const val TAG = "HandWritingDialog_D210"
@Composable
fun HandWritingDialog(
    handWritingViewModel: HandWritingViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel,
    onDismiss: () -> Unit,
    roomId: Int,
) {
    Log.d(TAG, "HandWritingDialog: on")
    
    val requestCashState by handWritingViewModel.cashResult.collectAsState()
    NetworkResultHandler(
        state = requestCashState,
        errorAction = {
            Log.d(TAG, "HandWritingDialog: 등록 실패")
        },
        successAction = {
            Log.d(TAG, "HandWritingDialog: ${it.result}")
            onDismiss()
            handWritingViewModel.initCashResult()
            homeViewModel.getTravelRoomInfo(roomId)
        }
    )
    
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "현금 결제 내역 입력",
                    style = pretendardBold20
                )
            
                Spacer(modifier = Modifier.height(4.dp))
            
                Image(
                    painter = painterResource(id = R.drawable.ic_coins),
                    contentDescription = "main icon"
                )
            
                Spacer(modifier = Modifier.height(4.dp))
            
                TextFieldWithTitle(
                    title = "사용내역",
                    hint = "사용 내역을 입력해 주세요.",
                    value = handWritingViewModel.useMoneyContent.value,
                    onValueChange = {
                        handWritingViewModel.setUseMoneyContent(it)
                    },
                    keyboardType = KeyboardType.Text
                )
            
                Spacer(modifier = Modifier.height(16.dp))
            
                TextFieldWithTitle(
                    title = "금액",
                    hint = "금액을 입력해주세요.",
                    value = handWritingViewModel.useMoney.value,
                    onValueChange = {
                        handWritingViewModel.setUseMoney(it)
                    },
                    keyboardType = KeyboardType.Number
                )
            
                Spacer(modifier = Modifier.height(16.dp))
                
                MinionButtonSet(
                    modifier = Modifier.fillMaxWidth(),
                    contentLeft = "입력",
                    onClickLeft = {
                        /**
                         * 입력시 viewModel -> usecase -> api 로 통신 -> 실패시 알려주기
                         * 홈 화면 로딩 -> 변경된 데이터 화면에 보이기
                         */
                        if(handWritingViewModel.checkInput()){
                            handWritingViewModel.requestCash(roomId)
                        } else {
                        
                        }
                    },
                    contentRight = "취소",
                    onClickRight = onDismiss
                )
            }
        
        }
    }
}




