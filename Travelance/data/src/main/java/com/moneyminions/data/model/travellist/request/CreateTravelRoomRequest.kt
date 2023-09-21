package com.moneyminions.data.model.travellist.request

data class CreateTravelRoomRequest(
    var budget: Int,
    var endDate: String,
    var location: String,
    var startDate: String,
    var travelName: String
)
