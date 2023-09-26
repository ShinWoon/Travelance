package com.moneyminions.presentation.screen.travellist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel

@Composable
fun ProfileDialog(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    Dialog(
      onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(24.dp, 32.dp, 24.dp, 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
    
                ProfileImage()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                TextFieldWithTitle(
                    title = "닉네임",
                    hint = "닉네임을 입력해주세요.",
                    value = createTravelViewModel.nickName.value,
                    onValueChange = {
                        createTravelViewModel.setNickName(it)
                    }
                )
    
                Spacer(modifier = Modifier.height(32.dp))
                
                MinionPrimaryButton(
                    content = "완료",
                    modifier = Modifier,
                    onClick = {
                        // todo API 호출 방 생성 + 프로필 정보
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileImage(

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MinionProfile(size = 120.dp)
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = "사진 추가",
            color = PinkDarkest,
            style = CustomTextStyle.pretendardBold12,
            modifier = Modifier.clickable {
                // todo : 사진 추가하기
            }
        )
    }
}