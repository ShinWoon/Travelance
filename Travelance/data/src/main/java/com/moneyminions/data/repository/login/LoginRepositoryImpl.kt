package com.moneyminions.data.repository.login

import android.util.Log
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

private const val TAG = "LoginRepositoryImpl D210"
class LoginRepositoryImpl @Inject constructor(
    val loginDataSource: LoginDataSource,
    val preferenceDataSource: PreferenceDataSource
): LoginRepository {
    override suspend fun login(socialType: String): NetworkResult<LoginResultDto> {
        Log.d(TAG, "login fcm ${preferenceDataSource.getFCMToken()}")
        return handleApi { loginDataSource.login(LoginRequest(socialType, preferenceDataSource.getFCMToken())).toDomain() }
    }

    override suspend fun postAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<AuthenticationAccountResultDto> {
        return handleApi { loginDataSource.postAuthenticationAccount(accountInfoDto.toData()).toDomain() }
    }

    override suspend fun confirmAuthenticationAccount(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<CommonResultDto> {
        return handleApi { loginDataSource.confirmAuthenticationAccount(accountInfoDto.toData()).toDomain() }
    }

    override suspend fun getAccountList(): NetworkResult<List<AccountDto>> {
        return handleApi { loginDataSource.getAccountList().map {
            Log.d(TAG, "getAccountList $it")
            it.toDomain() } }
    }

    override suspend fun getCardList(): NetworkResult<List<CardDto>> {
        return handleApi { loginDataSource.getCardList().map { it.toDomain() } }
    }

    override suspend fun join(memberInfo: MemberInfo): NetworkResult<JwtTokenDto> {
        return handleApi { loginDataSource.join(memberInfo.toData()).toDomain() }
    }

    override suspend fun logout(): NetworkResult<CommonResultDto> {
        return handleApi { loginDataSource.logout().toDomain() }
    }

    override suspend fun joinOut(): NetworkResult<CommonResultDto> {
        return handleApi { loginDataSource.joinOut().toDomain() }
    }

}