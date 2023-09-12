package com.moneyminions.presentation.screen.mypage

data class AccountDto(
    val logo: String,
    val name: String,
    val number: String,
    var isSelected: Boolean? = false
)