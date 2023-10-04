package com.moneyminions.domain.model.home

data class TravelMemberUseDto(
    var paymentId: Int = 0,
    var price: Int = 0,
    var content: String = "",
    var category: String? = "",
    var address: String? = "",
    var memberId: Int = 0,
    var nickName: String = "",
    var paymentAt: String = "",
    var profileUrl: String = "",
)