package com.moneyminions.data.model.travellist.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CreateTravelRoomRequest(
    @SerializedName("roomUserRequestDto")
    var roomUserRequestDto: RoomUserRequest,

    @SerializedName("roomInfoRequestDto")
    var roomInfoRequestDto: RoomInfoRequest,

    @SerializedName("imageFiles")
    var imageFiles: MultipartBody.Part?
)
