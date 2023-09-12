package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.White

@Composable
fun AccountColumnItem(
    logo: String,
    compoany: String,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        AsyncImage(
            model = logo,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = compoany,
            style = CustomTextStyle.pretendardMedium08
        )
        if (isSelected) {
            // 선택됐을 때 색 채운 원
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = PinkDarkest, shape = CircleShape)
                    .border(1.dp, DarkGray, shape = CircleShape)
            )
        } else {
            // 선택되지 않았을 때 빈 원
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = Color.Transparent, shape = CircleShape)
                    .border(1.dp, DarkGray, shape = CircleShape)
            )
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountColumnItemPreview(){
    AccountColumnItem(logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        compoany = "신한",
        isSelected = false,
        {})
}