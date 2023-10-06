package com.moneyminions.data.model.travelmap.response


import com.google.gson.annotations.SerializedName

data class TravelMapDetailResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("paymentAt")
    val paymentAt: String,
    @SerializedName("price")
    val price: Int
)