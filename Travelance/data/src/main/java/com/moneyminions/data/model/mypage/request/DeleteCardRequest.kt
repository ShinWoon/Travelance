package com.moneyminions.data.model.mypage.request


import com.google.gson.annotations.SerializedName

data class DeleteCardRequest(
    @SerializedName("cardCoName")
    val cardCoName: String,
    @SerializedName("cardNumber")
    val cardNumber: String
)