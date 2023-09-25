package com.moneyminions.data.model.travellist.request


import com.google.gson.annotations.SerializedName

data class TravelListRequest(
    @SerializedName("email")
    var email: String,
    @SerializedName("role")
    var role: String
)