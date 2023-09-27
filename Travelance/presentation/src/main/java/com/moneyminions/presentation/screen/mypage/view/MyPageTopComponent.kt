package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.theme.DarkerGray

@Composable
fun MyPageTopComponent(
    nickname: String,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = nickname, // 여기 유저의 닉네임이 들어가야함
            style = CustomTextStyle.pretendardBold24,
        )
        IconButton(
            onClick = {onClick()}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_setting),
                tint = DarkerGray,
                contentDescription = "setting",
            )
        }
    }
}