package com.moneyminions.domain.model.common

data class CardDto(
    val name: String,
    val number: String,
    val idx: Int,
    val company: String,
    val logo: String,
    var isSelected: Boolean? = false
)