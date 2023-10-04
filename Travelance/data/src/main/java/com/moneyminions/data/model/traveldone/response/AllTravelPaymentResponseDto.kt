package com.moneyminions.data.model.traveldone.response


import com.google.gson.annotations.SerializedName

data class AllTravelPaymentResponseDto(
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
    val storeSector: String
)