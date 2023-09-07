package com.moneyminions.presentation.common.text

import androidx.compose.ui.text.font.FontFamily
import com.moneyminions.presentation.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp

object CustomTextStyle{

    val appBarTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_extra_bold)),
        fontSize = 32.sp
    )

    val bigTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 24.sp
    )

    val regularTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 20.sp
    )

    val smallTitleTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 16.sp
    )

    val regularTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
        fontSize = 12.sp
    )

    val grayLightTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontSize = 8.sp,
        //회색 설정해줘야 함
    )

    val blackLightTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_light)),
        fontSize = 8.sp
    )

    val whiteButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 24.sp
        //색 하얗게
    )

    val blackButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontSize = 24.sp
    )

    val smallWhiteButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
        fontSize = 16.sp
        //색 하얗게
    )

    val smallBlackButtonTextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semi_bold)),
        fontSize = 16.sp
    )


}