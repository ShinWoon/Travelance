package com.moneyminions.data.model.login.response


import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("account")
    val account: String,
    @SerializedName("accounterPrivatedId")
    val accounterPrivatedId: String,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("idx")
    val idx: Int
)