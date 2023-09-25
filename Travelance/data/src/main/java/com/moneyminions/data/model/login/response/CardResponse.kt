package com.moneyminions.data.model.login.response


import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("cardCoCode")
    val cardCoCode: String,
    @SerializedName("cardCoName")
    val cardCoName: String,
    @SerializedName("cardNumber")
    val cardNumber: String,
    @SerializedName("cardPassword")
    val cardPassword: String,
    @SerializedName("cvc")
    val cvc: String,
    @SerializedName("idx")
    val idx: Int
)