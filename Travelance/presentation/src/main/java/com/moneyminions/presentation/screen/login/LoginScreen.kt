package com.moneyminions.presentation.screen.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.login.view.KakaoLoginButtonComponent
import com.moneyminions.presentation.theme.KakaoLabelColor
import com.moneyminions.presentation.theme.KakaoYellow
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.login.LoginViewModel
import kotlinx.coroutines.launch

private const val TAG = "LoginScreen D210"
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val loginState by loginViewModel.loginResult.collectAsState()
    NetworkResultHandler(
        state = loginState,
        errorAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("로그인 실패")
            }
        },
        successAction = {
            Log.d(TAG, "loginResult : $it ")
            if(it.role == "MEMBER"){
                //TODO homeScreen으로 이동
            }
            if(it.role=="GUEST"){
                coroutineScope.launch {
                    loginViewModel.refreshNetworkState()
                }
                Log.d(TAG, "갱신 전 token : ${loginViewModel.getJwtToken()}")
                Log.d(TAG, "갱신 전 role : ${loginViewModel.getRole()}")
                loginViewModel.updateJwtToken(it.accessToken,it.refreshToken)
                loginViewModel.updateRole(it.role)
                Log.d(TAG, "갱신된 token : ${loginViewModel.getJwtToken()}")
                Log.d(TAG, "갱신된 role : ${loginViewModel.getRole()}")
                navController.navigate(Screen.AccountAuthentication.route){
                    //TODO Back stack 관리 어떻게 할 지 정해라
                }
            }
        }
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(White),
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PinkLight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_logo),
                        contentDescription = "main_logo",
                        modifier = Modifier.fillMaxSize(0.4f)
                    )
                    KakaoLoginButtonComponent(){
                        loginViewModel.singInKakao(context)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(navController = rememberNavController())
}
