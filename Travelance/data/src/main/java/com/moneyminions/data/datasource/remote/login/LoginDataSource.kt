package com.moneyminions.data.datasource.remote.login

import android.content.Context
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.login.LoginResponse

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}