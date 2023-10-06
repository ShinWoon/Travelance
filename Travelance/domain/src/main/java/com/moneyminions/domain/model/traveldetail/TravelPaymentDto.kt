package com.moneyminions.domain.model.traveldetail

import com.google.gson.annotations.SerializedName

data class TravelPaymentDto(
    val isWithPaid: Boolean = true,
    val paymentContent: String = "",
    val paymentAmount: Int = 0,
    val paymentAt: String = "",
    val paymentId: Int = 0,
    val storeAddress: String = "",
    val storeSector: String = "",
)
