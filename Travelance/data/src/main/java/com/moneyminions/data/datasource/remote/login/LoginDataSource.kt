package com.moneyminions.data.datasource.remote.login

import android.content.Context
import com.moneyminions.data.model.response.login.LoginResponse

interface LoginDataSource {
    suspend fun login(): LoginResponse
}