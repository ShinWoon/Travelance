package com.moneyminions.data.mapper

import com.moneyminions.data.model.login.response.login.LoginResponse
import com.moneyminions.domain.model.login.LoginResultDto

fun LoginResponse.toDomain(): LoginResultDto {
    return LoginResultDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        role = role
    )
}