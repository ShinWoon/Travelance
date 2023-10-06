package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditName(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
){
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
    )

    Text(
        text = "닉네임",
        style = CustomTextStyle.pretendardBold16
    )
    Spacer(
        modifier = Modifier.size(16.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "닉네임을 입력해주세요",
                    style = CustomTextStyle.pretendardRegular12,
                    color = GraphGray
                )
            },
            singleLine = true,
            colors = outlinedTextFieldColors,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(0.7f) // 80%의 가중치
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Button(
            modifier = Modifier.weight(0.2f),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkDarkest,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            onClick = onClick
        ) {
            Text(
                text = "수정",
                style = CustomTextStyle.pretendardBold12,
                color = White
            )
        }
    }
}