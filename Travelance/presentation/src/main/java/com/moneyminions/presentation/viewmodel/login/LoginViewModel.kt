package com.moneyminions.presentation.viewmodel.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.moneyminions.domain.usecase.login.SignInKakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kakao.sdk.user.UserApiClient
import javax.security.auth.callback.Callback

private const val TAG = "LoginViewModel D210"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInKakaoUseCase: SignInKakaoUseCase
): ViewModel() {

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when{
            error != null -> Log.e(TAG, "kakao login error 2 $error")
            token != null -> Log.d(TAG, "kakao login success $token")
        }
    }

    fun singInKakao(context: Context) {
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