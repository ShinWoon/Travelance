package com.moneyminions.domain.model.traveldetail

data class TravelDetailInfoDto(
    val friendPayments: List<FriendPaymentDto>,
    val travelPaymentResponseDto: List<TravelPaymentDto>,
    val travelRoomInfo: List<TravelRoomInfoDto>,
)
