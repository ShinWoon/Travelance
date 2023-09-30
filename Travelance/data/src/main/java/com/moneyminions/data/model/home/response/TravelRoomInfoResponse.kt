package com.moneyminions.data.model.home.response


import com.google.gson.annotations.SerializedName

data class TravelRoomInfoResponse(
    @SerializedName("roomId")
    var roomId: Int,
    @SerializedName("travelName")
    var travelName: String,
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("percent")
    var percent: Float,
    @SerializedName("rest")
    var rest: Int,
    @SerializedName("isDone")
    var isDone: String,
    @SerializedName("everyuse")
    var everyuse: List<TravelRoomMemberUseResponse>,
    @SerializedName("myuse")
    var myuse: List<TravelRoomMemberUseResponse>,
    @SerializedName("useTotal")
    var useTotal: Int,
)