package com.moneyminions.data.model.traveldone.response


import com.google.gson.annotations.SerializedName

data class TravelDoneInfoResponseDto(
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("travelName")
    val travelName: String,
    @SerializedName("use")
    val use: Int
)