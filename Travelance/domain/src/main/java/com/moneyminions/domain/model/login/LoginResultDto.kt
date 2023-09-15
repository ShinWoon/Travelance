package com.moneyminions.domain.model.login

data class LoginResultDto(
    val tokenId: String,
    val name: String,
    val role: String
)