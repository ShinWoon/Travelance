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
import com.moneyminions.domain.usecase.preference.GetRoleUseCase
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
                Log.d(TAG, "LoginScreen에서 member라서 바로 home으로 $it")
                loginViewModel.updateJwtToken(it.accessToken,it.refreshToken,it.role)
                //TODO homeScreen으로 이동
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
            }else{
                coroutineScope.launch {
                    loginViewModel.refreshNetworkState()
                }
                Log.d(TAG, "갱신 전 token : ${loginViewModel.getJwtToken()}")
                loginViewModel.updateJwtToken(it.accessToken,it.refreshToken,it.role)
                Log.d(TAG, "갱신된 token : ${loginViewModel.getJwtToken()}")
                navController.navigate(Screen.AccountAuthentication.route)
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
//    LoginScreen(navController = rememberNavController())
}
