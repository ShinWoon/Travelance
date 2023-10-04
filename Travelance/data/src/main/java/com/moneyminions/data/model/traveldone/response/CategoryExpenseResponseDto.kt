package com.moneyminions.data.model.traveldone.response

import com.google.gson.annotations.SerializedName

data class CategoryExpenseResponseDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("percent")
    val percent: Double,
)
