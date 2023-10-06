package com.moneyminions.domain.model.travelmap

data class LocationDto(
    val storeAddress: String = "",
    val storeCategory: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)