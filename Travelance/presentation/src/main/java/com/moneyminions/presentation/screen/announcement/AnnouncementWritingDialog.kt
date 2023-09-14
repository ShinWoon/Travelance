package com.moneyminions.presentation.screen.announcement

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
import androidx.compose.material.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.viewmodel.announcement.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementWritingDialog(
    announcementViewModel: AnnouncementViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
        placeholderColor = GraphGray // 힌트 색상
    )
    var isHintVisible by remember { mutableStateOf(announcementViewModel.content.value.isEmpty()) }
    
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "새 게시글",
                        style = CustomTextStyle.pretendardBold20
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_notebook),
                        contentDescription = "notebook icon"
                    )
                }
    
                Spacer(modifier = Modifier.height(16.dp))
                
                TextFieldWithTitle(
                    title = "제목",
                    hint = "제목을 입력해주세요.",
                    value = announcementViewModel.title.value,
                    onValueChange = {
                        announcementViewModel.setTitle(it)
                    }
                )
    
                Spacer(modifier = Modifier.height(16.dp))
    
    
                Text(
                    text = "내용",
                    style = CustomTextStyle.pretendardBold16,
                    modifier = Modifier.layoutId("textTitle")
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
    
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
                            color = GraphGray
                        )
                    },
                    singleLine = false,
                    colors = outlinedTextFieldColors,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .layoutId("textField")
                        .fillMaxWidth()
                        .height(100.dp)
                )
    
                
            }
        }
    }
}