package com.moneyminions.data.model.traveldetail.response

import com.google.gson.annotations.SerializedName

data class TravelDetailInfoResponse(
    @SerializedName("friendPayments")
    val friendPayments: List<FriendPaymentResponse>,
    @SerializedName("travelPaymentResponseDto")
    val travelPaymentResponseDto: List<TravelPaymentResponse>,
    @SerializedName("travelRoomInfo")
    val travelRoomInfo: List<TravelRoomInfoResponse>,
)
