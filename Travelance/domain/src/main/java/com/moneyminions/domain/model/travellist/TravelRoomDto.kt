package com.moneyminions.domain.model.travellist

data class TravelRoomDto(
    var budget: Int,
    var endDate: String,
    var isDone: String,
    var location: String,
    var roomId: Int,
    var startDate: String,
    var travelName: String,
    var use: Int
)