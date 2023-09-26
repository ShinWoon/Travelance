package com.moneyminions.domain.model.travellist

data class TravelRoomDto(
    var budget: Int = 0,
    var endDate: String = "",
    var isDone: String = "",
    var roomId: Int = 0,
    var startDate: String = "",
    var travelName: String = "",
    var use: Int = 0,
)