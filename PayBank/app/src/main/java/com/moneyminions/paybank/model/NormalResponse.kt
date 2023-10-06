package com.moneyminions.paybank.model


import com.google.gson.annotations.SerializedName

data class NormalResponse(
    @SerializedName("result")
    val result: String
)