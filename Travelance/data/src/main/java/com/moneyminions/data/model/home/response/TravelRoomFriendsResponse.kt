package com.moneyminions.data.model.home.response


import com.google.gson.annotations.SerializedName

data class TravelRoomFriendsResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("profileUrl")
    val profileUrl: String,
    @SerializedName("travelNickname")
    val travelNickname: String
)