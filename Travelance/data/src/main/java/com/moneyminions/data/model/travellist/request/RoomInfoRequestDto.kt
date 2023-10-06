package com.moneyminions.data.model.travellist.request


import com.google.gson.annotations.SerializedName

data class RoomInfoRequestDto(
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("endDate")
    var endDate: String,
    @SerializedName("startDate")
    var startDate: String,
    @SerializedName("travelName")
    var travelName: String
)