package com.moneyminions.data.model.mypage.request


import com.google.gson.annotations.SerializedName

data class DeleteAccountRequest(
    @SerializedName("account")
    val account: String,
    @SerializedName("accountName")
    val accountName: String
)