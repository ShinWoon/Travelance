package com.moneyminions.paybank.model


import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("cardNumber")
    val cardNumber: String,
    @SerializedName("cvc")
    val cvc: String,
    @SerializedName("paymentAmount")
    val paymentAmount: Int,
    @SerializedName("paymentContent")
    val paymentContent: String,
    @SerializedName("storeAddress")
    val storeAddress: String,
    @SerializedName("storeSector")
    val storeSector: String
)