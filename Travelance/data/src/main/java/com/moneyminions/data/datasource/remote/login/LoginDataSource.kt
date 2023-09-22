package com.moneyminions.data.datasource.remote.login

import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun postAuthenticationAccount(authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse
}