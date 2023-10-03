package com.moneyminions.data.model.traveldetail.request


import com.google.gson.annotations.SerializedName

data class PaymentCompleteRequest(
    @SerializedName("paymentWithList")
    val paymentWithList: List<PaymentWithRequest>,
    @SerializedName("roomNumber")
    val roomNumber: Int
)