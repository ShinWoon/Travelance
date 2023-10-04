package com.moneyminions.data.model.travelmap.response


import com.google.gson.annotations.SerializedName

data class TravelMapInfoResponse(
    @SerializedName("storeAddress")
    val storeAddress: String?,
    @SerializedName("storeSector")
    val storeSector: String?
)