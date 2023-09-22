package com.moneyminions.domain.model.login

data class LoginResultDto(
    val accessToken: String?,
    val refreshToken: String?,
    val role: String?
)