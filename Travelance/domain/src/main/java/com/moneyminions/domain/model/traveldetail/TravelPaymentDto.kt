package com.moneyminions.domain.model.traveldetail

data class TravelPaymentDto(
    val isWithPaid: Boolean = true,
    val paymentContent: String = "",
    val paymentAmount: Int = 0,
    val paymentAt: String = "",
    val paymentId: Int = 0,
)
