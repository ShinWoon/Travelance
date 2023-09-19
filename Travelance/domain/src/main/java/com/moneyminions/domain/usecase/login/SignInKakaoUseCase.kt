package com.moneyminions.domain.usecase.login

import android.content.Context
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class SignInKakaoUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
}