package com.moneyminions.presentation.utils
import java.text.SimpleDateFormat

object DateUtils {
    fun DashToSlash(input: String): String {
        return input.replace("-", "/")
    }
    
    fun DashToDot(input: String): String {
        return input.replace("-", ".")
    }
    
    fun makeDate(inputDate: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputDateFormat = SimpleDateFormat("yy.MM.dd HH:mm")
    
        try {
            val date = inputDateFormat.parse(inputDate)
            val outputDateString = outputDateFormat.format(date)
            return outputDateString
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
