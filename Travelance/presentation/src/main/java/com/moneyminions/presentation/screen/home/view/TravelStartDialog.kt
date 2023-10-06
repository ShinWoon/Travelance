package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionButtonSet
import com.moneyminions.presentation.viewmodel.home.HomeViewModel

@Composable
fun TravelStartDialog(
    homeViewModel: HomeViewModel,
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "여행을 시작 하시겠어요?",
                    style = CustomTextStyle.pretendardBold20
                )
    
                Spacer(modifier = Modifier.height(16.dp))

                MinionButtonSet(
                    modifier = Modifier.fillMaxWidth(),
                    contentLeft = "시작",
                    onClickLeft = {
                        // 여행 시작 로직 처리
                        homeViewModel.startTravel(homeViewModel.travelRoomInfo.value.roomId)
                        homeViewModel.setTravelStart(true)
                    },
                    contentRight = "취소",
                    onClickRight = onDismiss
                )
            }
        }
    }
}