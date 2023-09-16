package com.moneyminions.data.model.response.login

data class LoginResponse(
    val tokenId: String,
    val name: String,
    val role: String
)