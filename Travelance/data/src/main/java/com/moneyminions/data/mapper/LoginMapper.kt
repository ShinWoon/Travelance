package com.moneyminions.data.mapper

import com.moneyminions.data.model.response.login.LoginResponse
import com.moneyminions.domain.model.login.LoginResultDto

fun LoginResponse.toDomain(): LoginResultDto {
    return LoginResultDto(
        tokenId = tokenId,
        name = name,
        role = role
    )
}