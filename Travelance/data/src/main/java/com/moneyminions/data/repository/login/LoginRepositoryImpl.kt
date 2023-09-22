package com.moneyminions.data.repository.login

import android.content.Context
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.mapper.example.toDomain
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class LoginRepositoryImpl @Inject constructor(
    val loginDataSource: LoginDataSource,
    val preferenceDataSource: PreferenceDataSource
): LoginRepository {
    override suspend fun login(socialType: String): NetworkResult<LoginResultDto> {
        return handleApi { loginDataSource.login(LoginRequest(socialType)).toDomain() }
    }

    override suspend fun postAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<AuthenticationAccountResultDto> {
        return handleApi { loginDataSource.postAuthenticationAccount(accountInfoDto.toData()).toDomain() }
    }

    override suspend fun confirmAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<CommonResultDto> {
        return handleApi { loginDataSource.confirmAuthenticationAccount(accountInfoDto.toData()).toDomain() }
    }

    override suspend fun getAccountList() {
        loginDataSource.getAccountList()
    }

}