package com.moneyminions.domain.model.traveldetail

data class FriendPaymentDto(
    val done: Boolean = false,
    val nickName: String = "",
    val paymentAmount: Int = 0,
    val profileUrl: String? = "",
)
