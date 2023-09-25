package com.moneyminions.domain.model.common

data class CardDto(
    val name: String = "",
    val number: String,
    val idx: Int,
    var isSelected: Boolean? = false
)