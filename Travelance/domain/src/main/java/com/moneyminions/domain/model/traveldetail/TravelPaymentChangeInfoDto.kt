package com.moneyminions.domain.model.traveldetail


import com.google.gson.annotations.SerializedName

data class TravelPaymentChangeInfoDto(
    val paymentId: Int = 0,
    val withPaid: Boolean = true,
)