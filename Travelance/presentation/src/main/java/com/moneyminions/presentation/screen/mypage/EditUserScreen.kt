package com.moneyminions.presentation.screen.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    navController: NavHostController
){
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
        placeholderColor = GraphGray // 힌트 색상
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "회원 정보 수정"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Spacer(modifier = Modifier.size(16.dp))
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
                    value = "",
                    onValueChange = {

                    },
                    placeholder = {
                        Text(
                            text = "hint",
                            style = CustomTextStyle.pretendardRegular12,
                            color = GraphGray
                        )
                    },
                    singleLine = true,
                    colors = outlinedTextFieldColors,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .layoutId("textField")
                        .weight(0.7f) // 80%의 가중치
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .background(PinkDarkest)
                        .weight(0.2f) // 20%의 가중치
                ){
                    Text(
                        text = "완료",
                        style = CustomTextStyle.pretendardBold12,
                        color = White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun EditUserScreenPreview(){
    EditUserScreen(navController = rememberNavController())
}