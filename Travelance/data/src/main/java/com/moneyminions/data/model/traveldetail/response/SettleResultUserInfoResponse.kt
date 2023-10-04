package com.moneyminions.data.model.traveldetail.response


import com.google.gson.annotations.SerializedName

data class SettleResultUserInfoResponse(
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("paymentAmount")
    val paymentAmount: Int,
    @SerializedName("profileUrl")
    val profileUrl: String
)