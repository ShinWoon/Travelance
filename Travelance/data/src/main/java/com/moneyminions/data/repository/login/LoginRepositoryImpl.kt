package com.moneyminions.data.repository.login

import android.content.Context
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.mapper.example.toDomain
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class LoginRepositoryImpl @Inject constructor(
    val loginDataSource: LoginDataSource,
    val preferenceDataSource: PreferenceDataSource
): LoginRepository {
    override suspend fun login(): NetworkResult<LoginResultDto> {
        return handleApi { loginDataSource.login().toDomain() }
    }

}