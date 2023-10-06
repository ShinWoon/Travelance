package com.moneyminions.data.model.travellist.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CreateTravelRoomRequest(
    @SerializedName("roomUserRequestDto")
    var roomUserRequestDto: RoomUserRequestDto,
    
    @SerializedName("roomInfoRequestDto")
    var roomInfoRequestDto: RoomInfoRequestDto,
    
    @SerializedName("imageFiles")
    var imageFiles: MultipartBody.Part?
)
