package com.moneyminions.presentation.viewmodel.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kakao.sdk.user.UserApiClient
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.usecase.login.LoginUseCase
import com.moneyminions.domain.usecase.preference.PutJwtTokenUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

private const val TAG = "LoginViewModel D210"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val putJwtTokenUseCase: PutJwtTokenUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _loginResult = MutableStateFlow<NetworkResult<LoginResultDto>>(NetworkResult.Idle)
    val loginResult = _loginResult.asStateFlow()

    fun singInKakao(context: Context) {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "callback...")
            when{
                error != null -> Log.e(TAG, "kakao login error 2 $error")
                token != null -> {
                    Log.d(TAG, "kakao login success -> access Token : ${token.accessToken}")
                    Log.d(TAG, "kakao login success -> refresh Token : ${token.refreshToken}")
                    putJwtTokenUseCase.invoke(JwtTokenDto(token.accessToken, token.refreshToken))
                    //로그인 api 호출
                    viewModelScope.launch {
                        _loginResult.emit(loginUseCase.invoke())
                    }
                }
            }
        }

        Log.d(TAG, "singInKakao....")
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
            //카카오톡 설치 시
            UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
                if(error != null){
                    Log.e(TAG, "kakao login error 1")
                }
                if(error is ClientError && error.reason == ClientErrorCause.Cancelled){
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = kakaoCallback
                )
            }
        }else{
            //미설치 시
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = kakaoCallback
            )
        }
    }


}