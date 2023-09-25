package com.moneyminions.data.datasource.remote.login

import android.util.Log
import com.moneyminions.data.datasource.remote.common.response.CommonResponse
import com.moneyminions.data.model.common.response.AccountResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.service.BusinessService

private const val TAG = "LoginDataSourceImpl D210"
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

    override suspend fun getAccountList(): List<AccountResponse> {
        Log.d(TAG, "getAccountList: ${businessService.getAccountList()}")
        return businessService.getAccountList()
    }

}