package com.moneyminions.data.model.login.response

import com.google.gson.annotations.SerializedName

data class AuthenticationAccountResponse(
    @SerializedName("verifyCode")
    val verifyCode: String
)
