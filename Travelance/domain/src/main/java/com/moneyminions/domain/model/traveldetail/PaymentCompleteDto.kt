package com.moneyminions.domain.model.traveldetail


data class PaymentCompleteDto(
    val email: String = "",
    val paymentWithList: List<TravelPaymentDto> = listOf(),
    val roomNumber: Int = 0,
)