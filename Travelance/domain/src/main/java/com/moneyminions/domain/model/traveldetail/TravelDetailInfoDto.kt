package com.moneyminions.domain.model.traveldetail

data class TravelDetailInfoDto(
    val friendPayments: List<FriendPaymentDto> = listOf(FriendPaymentDto()),
    val travelPayment: List<TravelPaymentDto> = listOf(TravelPaymentDto()),
    val travelRoomInfo: List<TravelRoomInfoDto> = listOf(TravelRoomInfoDto()),
)
