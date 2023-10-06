package com.moneyminions.data.model.traveldetail.response


import com.google.gson.annotations.SerializedName

data class SettleResultPaymentInfoResponse(
    @SerializedName("myAmount")
    val myAmount: Int,
    @SerializedName("perAmount")
    val perAmount: Int,
    @SerializedName("totalAmount")
    val totalAmount: Int,
    @SerializedName("transferTotalAmount")
    val transferTotalAmount: Int
)