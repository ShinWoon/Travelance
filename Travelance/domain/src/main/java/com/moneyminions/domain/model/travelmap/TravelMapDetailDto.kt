package com.moneyminions.domain.model.travelmap

data class TravelMapDetailDto(
    val address: String = "",
    val category: String = "",
    val content: String = "",
    val nickName: String = "",
    val paymentAt: String = "",
    val price: Int = 0,
)