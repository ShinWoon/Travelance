package com.moneyminions.data.repository.login

import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    val loginDataSource: LoginDataSource,
    val preferenceDataSource: PreferenceDataSource
): LoginRepository {
    override fun signInKakao() {
        TODO("Not yet implemented")
    }
}