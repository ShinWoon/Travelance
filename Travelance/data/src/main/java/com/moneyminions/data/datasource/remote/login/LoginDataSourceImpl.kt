package com.moneyminions.data.datasource.remote.login

import com.moneyminions.data.datasource.remote.common.response.CommonResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.service.BusinessService

class LoginDataSourceImpl( //service 만들어야 함!
    private val businessService: BusinessService
): LoginDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return businessService.login(loginRequest)
    }

    override suspend fun postAuthenticationAccount(authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse {
        return businessService.postAuthenticationAccount(authenticationAccountRequest)
    }

    override suspend fun confirmAuthenticationAccount(authenticationAccountRequest: AuthenticationAccountRequest): CommonResponse {
        return businessService.confirmAuthenticationAccount(authenticationAccountRequest)
    }

    override suspend fun getAccountList() {
        businessService.getAccountList()
    }

}