package com.moneyminions.data.model.travellist.request


import com.google.gson.annotations.SerializedName

data class RoomUserRequest(
    @SerializedName("nickName")
    var nickName: String,
)