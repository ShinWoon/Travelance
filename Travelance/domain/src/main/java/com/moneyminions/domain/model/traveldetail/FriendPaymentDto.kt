package com.moneyminions.domain.model.traveldetail

data class FriendPaymentDto(
    val done: Boolean,
    val nickName: String,
    val paymentAmount: Int,
    val profileUrl: String,
)
