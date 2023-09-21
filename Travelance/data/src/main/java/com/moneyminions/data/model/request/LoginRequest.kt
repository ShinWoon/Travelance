package com.moneyminions.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("socialType")
    val socialType: String,
)