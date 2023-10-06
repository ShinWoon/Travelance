package com.moneyminions.data.model.travelmap.request


import com.google.gson.annotations.SerializedName

data class TravelMapDetailRequest(
    @SerializedName("storeAddress")
    val storeAddress: String
)