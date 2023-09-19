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
import com.moneyminions.presentation.utils.KakaoLogin
import javax.security.auth.callback.Callback

private const val TAG = "LoginViewModel D210"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInKakaoUseCase: SignInKakaoUseCase,
    private val kakaoLogin: KakaoLogin
): ViewModel() {

    fun singInKakao(context: Context) {
        kakaoLogin.singInKakao(context)
    }

}