package com.moneyminions.presentation.screen.handwriting

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.common.MinionButtonSet
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.viewmodel.handwriting.HandWritingViewModel


private const val TAG = "HandWritingDialog"
@Composable
fun HandWritingDialog(
    handWritingViewModel: HandWritingViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
//    val promptInfo = BiometricPrompt.PromptInfo.Builder()
//        .setTitle("Biometric login for my app")
//        .setSubtitle("Log in using your biometric credential")
//        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
//        .build()
    
    
    var context = LocalContext.current
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
//                        checkAvailableAuth(context)
                    },
                    contentRight = "취소",
                    onClickRight = onDismiss
                )
            }
        
        }
    }
}

//private fun checkAvailableAuth(context: Context) {
//    val biometricManager = BiometricManager.from(context)
//    when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
//        BiometricManager.BIOMETRIC_SUCCESS -> {
//            //  생체 인증 가능
//            createBiometricPrompt(context)
//        }
//        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//            //  기기에서 생체 인증을 지원하지 않는 경우
//        }
//        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//            Log.d("MainActivity", "Biometric facility is currently not available")
//        }
//        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//            //  생체 인식 정보가 등록되지 않은 경우
//        }
//        else -> {
//            //   기타 실패
//        }
//    }
//}
//
//private fun createBiometricPrompt(context: Context): BiometricPrompt {
//    val executor = ContextCompat.getMainExecutor(this)
//
//    val callback = object : BiometricPrompt.AuthenticationCallback() {
//        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//            super.onAuthenticationError(errorCode, errString)
//            Log.d(TAG, "$errorCode :: $errString")
//            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
//                //TODO - 생체 인식이 안될 경우 비밀번호 입력할 수 있도록 기능 추가
//            }
//        }
//
//        override fun onAuthenticationFailed() {
//            super.onAuthenticationFailed()
//            Log.d(TAG, "Authentication failed for an unknown reason")
//        }
//
//        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//            super.onAuthenticationSucceeded(result)
//            Log.d(TAG, "Authentication was successful")
//            // Proceed with viewing the private encrypted message.
//            showEncryptedMessage(result.cryptoObject)
//        }
//    }
//
//    return BiometricPrompt(this, executor, callback)
//}