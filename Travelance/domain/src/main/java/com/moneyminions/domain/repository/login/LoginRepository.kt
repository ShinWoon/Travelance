package com.moneyminions.domain.repository.login

import android.content.Context
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.LoginResultDto

interface LoginRepository {
    suspend fun login(): NetworkResult<LoginResultDto>
}