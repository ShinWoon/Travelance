package com.moneyminions.domain.model.login

data class AuthenticationAccountInfoDto(
    val name: String,
    val bankName: String,
    val accountNumber: String,
    val verifyCode: String? = ""
)
