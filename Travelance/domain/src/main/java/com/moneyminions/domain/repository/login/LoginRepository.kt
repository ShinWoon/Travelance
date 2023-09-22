package com.moneyminions.domain.repository.login

import android.content.Context
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.LoginResultDto

interface LoginRepository {
    suspend fun login(socialType: String): NetworkResult<LoginResultDto>
    suspend fun postAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<AuthenticationAccountResultDto>
}