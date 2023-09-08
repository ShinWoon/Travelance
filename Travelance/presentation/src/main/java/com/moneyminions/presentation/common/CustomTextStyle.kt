package com.moneyminions.presentation.common

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.moneyminions.presentation.R

object CustomTextStyle {

    val pretendardBoldBlack20 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 20.sp,
        letterSpacing = 2.sp
    )

    val pretendardBoldBlack16 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 16.sp,
        letterSpacing = 2.sp
    )
    
    val regularTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 20.sp,
        letterSpacing = 2.sp,
    )
    
    val littleTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 14.sp,
        letterSpacing = 2.sp,
    )

    val smallTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 16.sp,
    )

    val regularTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
        fontSize = 12.sp,
    )

    val grayLightTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontSize = 8.sp,
        // 회색 설정해줘야 함
    )

    val blackLightTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontSize = 8.sp,
    )

    val whiteButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 24.sp,
        // 색 하얗게
    )

    val blackButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 24.sp,
    )

    val smallWhiteButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
        fontSize = 16.sp,
        // 색 하얗게
    )

    val smallBlackButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
        fontSize = 16.sp,
    )

    val pretendardRegularGaphGray12 = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
        fontSize = 12.sp
    )
}
