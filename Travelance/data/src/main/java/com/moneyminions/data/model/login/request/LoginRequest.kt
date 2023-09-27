package com.moneyminions.data.model.login.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("socialType")
    val socialType: String,
    @SerializedName("firebaseToken")
    val fcmToken: String
)