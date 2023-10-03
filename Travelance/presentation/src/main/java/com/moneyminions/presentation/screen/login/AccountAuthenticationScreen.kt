package com.moneyminions.presentation.screen.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.presentation.common.AuthenticationDialog
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.login.view.AuthenticAccountListComponent
import com.moneyminions.presentation.screen.mypage.view.AccountColumnItem
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.utils.addFocusCleaner
import com.moneyminions.presentation.viewmodel.login.AccountAuthenticationViewModel
import com.moneyminions.presentation.viewmodel.mypage.EditUserViewModel
import kotlinx.coroutines.launch

private const val TAG = "AccountAuthenticationSc D210"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountAuthenticationScreen(
    navController: NavHostController,
    accountAuthenticationViewModel: AccountAuthenticationViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isShowDialogState = accountAuthenticationViewModel.isShowDialog.collectAsState()
    val authenticationState by accountAuthenticationViewModel.authenticationResult.collectAsState()
    NetworkResultHandler(
        state = authenticationState,
        errorAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("계좌 정보를 조회하는 데 실패했습니다.")
            }
        },
        successAction = {
//            navController.navigate(Screen.AccountList.route)
            accountAuthenticationViewModel.setIsShowDialog(true)
        }
    )
    val confirmResultState by accountAuthenticationViewModel.confirmResult.collectAsState()
    NetworkResultHandler(
        state = confirmResultState,
        errorAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("인증 코드를 다시 확인해주세요")
            }
        },
        successAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("1원 인증 확인")
                accountAuthenticationViewModel.refreshNetworkState()
            }
            accountAuthenticationViewModel.setVerifyCode("")
            navController.navigate(Screen.AccountList.route)
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .addFocusCleaner(
                    LocalFocusManager.current
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "이름",
                    hint = "이름을 입력하세요",
                    value = accountAuthenticationViewModel.name.value,
                    onValueChange = { accountAuthenticationViewModel.setName(it) }
                )
                Spacer(modifier = Modifier.size(16.dp))
                AuthenticAccountListComponent()
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "게좌번호",
                    hint = "계좌 번호를 입력하세요",
                    value = accountAuthenticationViewModel.accountNumber.value,
                    onValueChange = { accountAuthenticationViewModel.setAccountNumber(it) },
                    keyboardType = KeyboardType.Number
                )

            }
            MinionPrimaryButton(
                content = "인증",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (accountAuthenticationViewModel.validCheck()) {
                    accountAuthenticationViewModel.postAuthenticationAccount()
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("값을 제대로 입력하시오")
                    }
                }
            }
        }
    }


    AuthenticationDialog(
        navController = navController,
        showDialog = isShowDialogState.value,
        onDismiss = {
            accountAuthenticationViewModel.setIsShowDialog(false)
            Log.d(TAG, "취소 클릭 : ${accountAuthenticationViewModel.isShowDialog.value}")
        },
        type = "account",
        value = accountAuthenticationViewModel.verifyCode.value,
        onValueChange = {
            accountAuthenticationViewModel.setVerifyCode(it)
        },
        onConfirm = {
            accountAuthenticationViewModel.confirmAuthenticationAccount()
        }
    )
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountAuthenticationScreenPreview() {
    AccountAuthenticationScreen(navController = rememberNavController())
}