package com.moneyminions.data.datasource.remote.login

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.CardResponse
import com.moneyminions.data.model.login.response.JoinResponse
import com.moneyminions.data.model.login.response.LoginResponse

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun postAuthenticationAccount(authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse

    suspend fun confirmAuthenticationAccount(authenticationAccountRequest: AuthenticationAccountRequest): CommonResponse
    suspend fun getAccountList(): List<AccountResponse>
    suspend fun getCardList(): List<CardResponse>
    suspend fun join(memberInfoRequest: MemberInfoRequest): JoinResponse
    suspend fun logout(): CommonResponse
    suspend fun joinOut(): CommonResponse
}