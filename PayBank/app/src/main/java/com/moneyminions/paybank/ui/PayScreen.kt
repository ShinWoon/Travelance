package com.moneyminions.paybank.ui

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.moneyminions.paybank.R
import com.moneyminions.paybank.model.FcmTokenRequest
import com.moneyminions.paybank.util.Constants
import com.moneyminions.paybank.util.NetworkResultHandler
import com.moneyminions.paybank.util.createNotificationChannel
import com.moneyminions.paybank.util.initFirebase
import com.moneyminions.paybank.viewmodel.PayViewModel


private const val TAG = "PayScreen D210"
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PayScreen(
    context: Context,
    payViewModel: PayViewModel = hiltViewModel()
){
    val isShowDialogState by payViewModel.isShowDialog.collectAsState()
    val permissionList: List<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else{
            listOf()
        }
    val permissionState = rememberMultiplePermissionsState(permissions = permissionList)
    if (permissionState.allPermissionsGranted) { //모든 권한 허용된 상태
    } else if (permissionState.shouldShowRationale) {//한번 거절했을때
        Toast.makeText(context, "사용을 위해서 허가가 필요합니다!", Toast.LENGTH_LONG).show()
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
        payViewModel.setIsShowDialog(true)
    } else { //최초
        Log.d("권한", "LoginDetailScreen: 최초")
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
        if(isShowDialogState){
            Column {
                AlertDialog(
                    onDismissRequest = { payViewModel.setIsShowDialog(false) },
                    dialogTitle = "서비스 이용 알림",
                    dialogText = "해당 기능에 대한 권한 사용을 거부하였습니다. 기능 사용을 원하실 경우 휴대폰 설정 > 애플리케이션 관리자에서 해당 앱의 권한을 허용해주세요.",
                    icon = Icons.Default.Info
                )
            }
        }

    }

    val postPaymentResultState by payViewModel.postPaymentResult.collectAsState()
    NetworkResultHandler(
        state = postPaymentResultState,
        errorAction = {
            Log.d(TAG, "Payment post error... ")
        },
        successAction = {
            Log.d(TAG, "Payment post success... ")
        }
    )

    val scrollableState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
            .padding(16.dp)
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
                payViewModel.postPaymentRequest()
//                payViewModel.setCardNumber("")
//                payViewModel.setCvc("")
//                payViewModel.setAmount("")
//                payViewModel.setStoreName("")
//                payViewModel.setStoreType("")
//                payViewModel.setStoreAddress("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "결제"
            )
        }
    }

}


@Composable
fun AlertDialog(onDismissRequest: () -> Unit, dialogTitle: String, dialogText: String, icon: ImageVector) {
    Dialog(
        onDismissRequest = {onDismissRequest()}
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
        ){
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = dialogTitle
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = dialogText
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {onDismissRequest()}
                ) {
                    Text(text = "확인")
                }
            }
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
//    PayScreen()
}