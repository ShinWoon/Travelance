package com.moneyminions.data.datasource.remote.login

import com.moneyminions.data.model.login.response.LoginResponse

interface LoginDataSource {
    suspend fun login(): LoginResponse
}