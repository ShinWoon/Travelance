package com.moneyminions.presentation.screen.travellist.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle

@Composable
fun DateTextComponent(
    text: String,
    value: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            style = CustomTextStyle.pretendardSemiBold16
        )
        Spacer(
            modifier = Modifier.size(4.dp)
        )
        Text(
            text = value,
            style = CustomTextStyle.pretendardSemiBold16
        )
    }
}