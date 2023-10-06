package com.moneyminions.data.model.login.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessTokenExpirationPeriod")
    val accessTokenExpirationPeriod: String,
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("refreshTokenExpirationPeriod")
    val refreshTokenExpirationPeriod: String,
    @SerializedName("role")
    val role: String
)