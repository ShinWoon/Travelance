package com.moneyminions.data.model.login.response


import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessTokenExpirePeriod")
    val accessTokenExpirePeriod: String,
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("role")
    val role: String
)