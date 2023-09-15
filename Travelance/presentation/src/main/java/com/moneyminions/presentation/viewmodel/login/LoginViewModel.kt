package com.moneyminions.presentation.viewmodel.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient
import com.moneyminions.domain.usecase.example.ExampleUseCase
import com.moneyminions.domain.usecase.login.SignInKakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInKakaoUseCase: SignInKakaoUseCase
): ViewModel() {


    fun singInKakao(context: Context) {

    }


}