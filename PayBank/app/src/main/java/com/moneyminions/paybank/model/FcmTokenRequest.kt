package com.moneyminions.paybank.model


import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)