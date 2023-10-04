package com.moneyminions.data.model.traveldetail.request


import com.google.gson.annotations.SerializedName

data class FinalPaymentRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("roomNumber")
    val roomNumber: Int
)