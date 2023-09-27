package com.moneyminions.domain.model.traveldetail

data class TravelPaymentDto(
    val isWithPaid: Boolean,
    val paymentAmount: Int,
    val paymentAt: String,
    val paymentId: Int,
)
