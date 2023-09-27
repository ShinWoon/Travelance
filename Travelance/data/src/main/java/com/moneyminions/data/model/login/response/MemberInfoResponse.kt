package com.moneyminions.data.model.login.response

import com.google.gson.annotations.SerializedName

data class MemberInfoResponse(
    @SerializedName("accountList")
    val accountList: List<AccountResponse>,
    @SerializedName("cardList")
    val cardList: List<CardResponse>,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String
)
