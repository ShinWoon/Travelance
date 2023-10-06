package com.moneyminions.data.model.traveldetail.response

import com.google.gson.annotations.SerializedName

data class TravelDetailMyPaymentResponse(
    @SerializedName("isWithPaid")
    val isWithPaid: Boolean,
    @SerializedName("paymentAmount")
    val paymentAmount: Int,
    @SerializedName("paymentAt")
    val paymentAt: String,
    @SerializedName("paymentContent")
    val paymentContent: String,
    @SerializedName("paymentId")
    val paymentId: Int,
    @SerializedName("storeAddress")
    val storeAddress: String,
    @SerializedName("storeSector")
    val storeSector: String,
)
