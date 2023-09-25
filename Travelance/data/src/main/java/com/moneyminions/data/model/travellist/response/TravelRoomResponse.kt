package com.moneyminions.data.model.travellist.response


import com.google.gson.annotations.SerializedName

data class TravelRoomResponse(
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("endDate")
    var endDate: String,
    @SerializedName("isDone")
    var isDone: String,
    @SerializedName("roomId")
    var roomId: Int,
    @SerializedName("startDate")
    var startDate: String,
    @SerializedName("travelName")
    var travelName: String,
    @SerializedName("use")
    var use: Int
)