package com.moneyminions.data.model.travellist.request

import com.google.gson.annotations.SerializedName

data class CreateTravelRoomRequest(
    @SerializedName("roomUserRequestDto")
    var roomUserRequestDto: RoomUserRequest,
    @SerializedName("roomInfoRequestDto")
    var roomInfoRequestDto: RoomInfoRequest,
)
