package com.moneyminions.presentation.utils

import java.text.DecimalFormat

object MoneyUtils {
    fun makeComma(num: Int): String {
        var comma = DecimalFormat("#,###")
        return "${comma.format(num)}원"
    }

    fun makeCommaWon(num: Int): String {
        var comma = DecimalFormat("#,###")
        return "\u20A9${comma.format(num)}"
    }
}
