package com.moneyminions.domain.model.home

data class TravelRoomInfoDto(
    var roomId: Int = 0,
    var travelName: String = "",
    var budget: Int = 0,
    var percent: Int = 0,
    var rest: Int = 0,
    var isDone: String = "",
    var everyuse: MutableList<TravelMemberUseDto> = mutableListOf(),
    var myuse: MutableList<TravelMemberUseDto> = mutableListOf(),
    var useTotal: Int = 0,
)