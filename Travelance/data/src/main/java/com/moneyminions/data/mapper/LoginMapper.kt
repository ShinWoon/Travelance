package com.moneyminions.data.mapper

import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.LoginResultDto

fun LoginResponse.toDomain(): LoginResultDto {
    return LoginResultDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        role = role
    )
}

fun AuthenticationAccountInfoDto.toData(): AuthenticationAccountRequest{
    return AuthenticationAccountRequest(
        name = name,
        bankName = bankName,
        account = accountNumber,
        verifycode = ""
    )
}
fun AuthenticationAccountResponse.toDomain(): AuthenticationAccountResultDto{
    return AuthenticationAccountResultDto(
        verifyCode = verifyCode
    )
}