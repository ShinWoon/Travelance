package com.moneyminions.data.model.traveldone.response


import com.google.gson.annotations.SerializedName

data class RoomUserResponseDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("travelNickname")
    val travelNickname: String
)