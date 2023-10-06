package com.moneyminions.data.model.travellist.request


import com.google.gson.annotations.SerializedName

data class RoomUserRequestDto(
    @SerializedName("nickName")
    var nickName: String,
)