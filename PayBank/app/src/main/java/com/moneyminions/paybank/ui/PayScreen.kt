package com.moneyminions.paybank.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.paybank.MainActivity
import com.moneyminions.paybank.model.FcmTokenRequest
import com.moneyminions.paybank.util.Constants
import com.moneyminions.paybank.viewmodel.PayViewModel

@Composable
fun PayScreen(
    payViewModel: PayViewModel = hiltViewModel()
){

    val scrollableState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
            .padding(16.dp)
            .imePadding()
            .addFocusCleaner(
                LocalFocusManager.current
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                //FCM 토큰 전송 API
                payViewModel.postFcmToken(FcmTokenRequest(Constants.fcmToken))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("FCM 토큰 전송")
        }
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "카드 번호",
            hint = "카드 번호를 입력하시오",
            value = payViewModel.cardNumber.value,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                payViewModel.setCardNumber(it)
            },
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "cvc",
            hint = "카드 뒷면에 있는 cvc를 입력하시오",
            value = payViewModel.cvc.value,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                payViewModel.setCvc(it)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "결제 금액",
            hint = "결제 금액을 입력하시오",
            value = payViewModel.amount.value,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                payViewModel.setAmount(it)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "가맹점명",
            hint = "가맹점명을 입력하시오",
            value = payViewModel.storeName.value,
            onValueChange = {
                payViewModel.setStoreName(it)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "가맹점 업종",
            hint = "가맹점 업종을 입력하시오",
            value = payViewModel.storeType.value,
            onValueChange = {
                payViewModel.setStoreType(it)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextFieldWithTitle(
            title = "가맹점 주소",
            hint = "가맹점 주소를 입력하시오",
            value = payViewModel.storeAddress.value,
            onValueChange = {
                payViewModel.setStoreAddress(it)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                //결제 api 호출
                payViewModel.setCardNumber("")
                payViewModel.setCvc("")
                payViewModel.setAmount("")
                payViewModel.setStoreName("")
                payViewModel.setStoreType("")
                payViewModel.setStoreAddress("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "결제"
            )
        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PayScreenPreview(){
    PayScreen()
}