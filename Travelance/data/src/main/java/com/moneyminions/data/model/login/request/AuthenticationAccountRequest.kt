package com.moneyminions.data.model.login.request


import com.google.gson.annotations.SerializedName

data class AuthenticationAccountRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("account")
    val account: String,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("verifyCode")
    val verifyCode: String
)