package com.moneyminions.domain.model.common

data class AccountDto(
    val idx: Int? = 0,
    val bankName: String,
    val accountNumber: String? = "",
    var isSelected: Boolean? = false
)