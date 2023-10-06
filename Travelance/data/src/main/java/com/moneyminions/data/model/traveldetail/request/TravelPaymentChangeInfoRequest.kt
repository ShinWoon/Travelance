package com.moneyminions.data.model.traveldetail.request


import com.google.gson.annotations.SerializedName

data class TravelPaymentChangeInfoRequest(
    @SerializedName("paymentId")
    val paymentId: Int,
    @SerializedName("withPaid")
    val withPaid: Boolean
)