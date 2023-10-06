package com.moneyminions.data.model.traveldetail.response


import com.google.gson.annotations.SerializedName

data class SettleResultRoomInfoResponse(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("travelName")
    val travelName: String
)