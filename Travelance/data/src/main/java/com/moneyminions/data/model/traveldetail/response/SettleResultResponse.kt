package com.moneyminions.data.model.traveldetail.response


import com.google.gson.annotations.SerializedName

data class SettleResultResponse(
    @SerializedName("paymentInfo")
    val paymentInfo: SettleResultPaymentInfoResponse,
    @SerializedName("receiveInfos")
    val receiveInfos: List<SettleResultUserInfoResponse>,
    @SerializedName("sendInfos")
    val sendInfos: List<SettleResultUserInfoResponse>,
    @SerializedName("travelRoomInfo")
    val travelRoomInfo: SettleResultRoomInfoResponse
)