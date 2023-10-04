package com.moneyminions.domain.repository.login

import android.content.Context
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto

interface LoginRepository {
    suspend fun login(socialType: String): NetworkResult<LoginResultDto>
    suspend fun postAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<AuthenticationAccountResultDto>
    suspend fun confirmAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<CommonResultDto>
    suspend fun getAccountList(): NetworkResult<List<AccountDto>>
    suspend fun getCardList(): NetworkResult<List<CardDto>>
    suspend fun join(memberInfo: MemberInfo): NetworkResult<JwtTokenDto>
    suspend fun logout(): NetworkResult<CommonResultDto>
    suspend fun joinOut(): NetworkResult<CommonResultDto>
}