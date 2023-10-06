package com.moneyminions.data.model.home.request


import com.google.gson.annotations.SerializedName

data class UseCashRequest(
    @SerializedName("paymentAmount")
    var paymentAmount: Int,
    @SerializedName("paymentContent")
    var paymentContent: String,
    @SerializedName("roomNumber")
    var roomNumber: Int
)