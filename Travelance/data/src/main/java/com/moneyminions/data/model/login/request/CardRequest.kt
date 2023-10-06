package com.moneyminions.data.model.login.request


import com.google.gson.annotations.SerializedName

data class CardRequest(
    @SerializedName("cardCoName")
    val cardCoName: String,
    @SerializedName("cardNumber")
    val cardNumber: String,
    @SerializedName("idx")
    val idx: Int?
)