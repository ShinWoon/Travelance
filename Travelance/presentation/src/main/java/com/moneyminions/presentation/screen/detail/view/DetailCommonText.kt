package com.moneyminions.presentation.screen.detail.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.theme.DarkerGray

@Composable
fun DetailCommonText(text: String) {
    Text(
        text = text,
        color = DarkerGray,
        style = CustomTextStyle.pretendardSemiBold16,
    )
}

@Composable
fun DetailCommonTitleText(text: String) {
    Text(
        text = text,
        color = DarkerGray,
        style = pretendardBold16,
    )
}
