package com.moneyminions.domain.model.travellist

import okhttp3.MultipartBody
data class CreateTravelRoomDto(
    var travelUserInfo: TravelUserDto = TravelUserDto(),
    var travelRoomInfo: TravelRoomDto = TravelRoomDto(),
    var imageFiles: MultipartBody.Part?, // 이미지 파일
)