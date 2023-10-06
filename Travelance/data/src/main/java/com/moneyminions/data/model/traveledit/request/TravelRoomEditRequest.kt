package com.moneyminions.data.model.traveledit.request


import com.google.gson.annotations.SerializedName

data class TravelRoomEditRequest(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("travelName")
    val travelName: String
)