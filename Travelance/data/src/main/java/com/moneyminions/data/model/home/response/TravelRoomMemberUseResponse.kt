package com.moneyminions.data.model.home.response


import com.google.gson.annotations.SerializedName

data class TravelRoomMemberUseResponse(
    @SerializedName("paymentId")
    var paymentId: Int,
    @SerializedName("price")
    var price: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("category")
    var category: String?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("memberId")
    var memberId: Int,
    @SerializedName("nickName")
    var nickName: String,
    @SerializedName("paymentAt")
    var paymentAt: String,
    @SerializedName("profileUrl")
    var profileUrl: String,
)