package com.moneyminions.presentation.utils

object DateUtils {
    fun DashToSlash(input: String): String {
        return input.replace("-", "/")
    }
    fun DashToDot(input: String): String {
        return input.replace("-",".")
    }
}
