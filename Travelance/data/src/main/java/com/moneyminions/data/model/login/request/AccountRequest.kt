package com.moneyminions.data.model.login.request


import com.google.gson.annotations.SerializedName

data class AccountRequest(
    @SerializedName("account")
    val account: String,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("idx")
    val idx: Int?
)