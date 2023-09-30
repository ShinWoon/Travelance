package com.moneyminions.domain.model.home

data class CashDto(
    var roomNumber: Int = 0,
    var paymentContent: String = "",
    var paymentAmount: Int = 0,
)
