package com.moneyminions.domain.model.login

data class JwtTokenDto(
    val accessToken: String? = "",
    val refreshToken: String? = "",
    val role: String? = ""
)
