package com.moneyminions.domain.model.traveldetail

data class TravelRoomInfoDto(
    val budget: Int = 0,
    val endDate: String = "",
    val startDate: String = "",
    val travelName: String = "",
    val done: Boolean = true,
)
