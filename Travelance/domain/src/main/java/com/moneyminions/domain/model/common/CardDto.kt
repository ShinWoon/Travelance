package com.moneyminions.domain.model.common

data class CardDto(
    val name: String,
    val number: String,
    val idx: Int? = 0,
    var isSelected: Boolean? = false
)