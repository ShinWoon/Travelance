package com.moneyminions.domain.model.travellist

data class CreateTravelRoomDto(
    var travelUserInfo: TravelUserDto = TravelUserDto(),
    var travelRoomInfo: TravelRoomDto = TravelRoomDto(),
)