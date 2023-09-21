package com.moneyminions.data.datasource.remote.login

import android.content.Context
import android.util.Log
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.model.response.login.LoginResponse
import com.moneyminions.data.service.BusinessService
import com.moneyminions.data.service.handleApi

class LoginDataSourceImpl( //service 만들어야 함!
    private val businessService: BusinessService
): LoginDataSource {
    override suspend fun login(): LoginResponse {
        return businessService.login()
    }

}