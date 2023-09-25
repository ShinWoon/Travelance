package com.moneyminions.data.model.login.request


import com.google.gson.annotations.SerializedName
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto

data class MemberInfoRequest(
    @SerializedName("accountList")
    val accountList: List<AccountRequest>,
    @SerializedName("cardList")
    val cardList: List<CardRequest>,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String
)