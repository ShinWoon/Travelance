package com.moneyminions.presentation.screen.announcement

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionButtonSet
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.Gray
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White
import com.moneyminions.presentation.viewmodel.announcement.AnnouncementViewModel

private const val TAG = "AnnouncementWritingDial_D210"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementWritingDialog(
    announcementViewModel: AnnouncementViewModel,
    roomId: Int,
//    announcementDto: AnnouncementDto,
    onDismiss: () -> Unit,
) {
    Log.d(TAG, "AnnouncementWritingDialog: $roomId")
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
    )
    var isHintVisible by remember { mutableStateOf(announcementViewModel.content.value.isEmpty()) }
    
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            color = White,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                TopComponent()
                
                Spacer(modifier = Modifier.height(16.dp))
                TextFieldWithTitle(
                    title = "제목",
                    hint = "제목을 입력해주세요.",
                    value = announcementViewModel.title.value,
                    onValueChange = {
                        announcementViewModel.setTitle(it)
                    },
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "내용",
                    style = CustomTextStyle.pretendardBold16,
                    modifier = Modifier.layoutId("textTitle"),
                )
                
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = announcementViewModel.content.value,
                    onValueChange = {
                        announcementViewModel.setContent(it)
                        isHintVisible = it.isEmpty()
                    },
                    placeholder = {
                        Text(
                            text = "내용을 입력해 주세요.",
                            style = CustomTextStyle.pretendardRegular12,
                            color = GraphGray,
                        )
                    },
                    singleLine = false,
                    colors = outlinedTextFieldColors,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                    ),
                    modifier = Modifier
                        .layoutId("textField")
                        .fillMaxWidth()
                        .height(100.dp),
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "링크 제목",
                    style = CustomTextStyle.pretendardBold16,
                    modifier = Modifier.layoutId("textTitle"),
                )
                
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = announcementViewModel.link.value,
                    onValueChange = {
                        announcementViewModel.setLink(it)
                    },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "링크",
                            style = CustomTextStyle.pretendardRegular12,
                            color = GraphGray,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .padding(0.dp),
                            painter = painterResource(id = R.drawable.ic_link),
                            contentDescription = "Localized description",
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = White,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = PinkLight,
                        unfocusedIndicatorColor = Gray,
                        disabledIndicatorColor = Gray,
                    ),
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                MinionButtonSet(
                    modifier = Modifier.fillMaxWidth(),
                    contentLeft = if(announcementViewModel.selectNoticeId.value == 0) "추가" else "수정",
                    onClickLeft = {
                        
                        if(announcementViewModel.checkInput()) {
                            if(announcementViewModel.selectNoticeId.value == 0) { // 저장
                                announcementViewModel.saveAnnouncement(roomId = roomId)
                            } else { // 삭제
                                announcementViewModel.editAnnouncement(roomId = roomId)
                            }
                            announcementViewModel.inputReset()
                            onDismiss()
                        }
                    },
                    contentRight = "취소",
                    onClickRight = onDismiss,
                )
            }
        }
    }
}

@Composable
fun TopComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "새 게시글",
            style = CustomTextStyle.pretendardBold20,
        )
        Image(
            painter = painterResource(id = R.drawable.ic_notebook),
            contentDescription = "notebook icon",
        )
    }
}
