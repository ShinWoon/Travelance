package com.moneyminions.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SimpleDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp)), // 모서리를 둥글게 만들기
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "정말 삭제하시겠습니까?",
                    style = CustomTextStyle.pretendardSemiBold16
                )
                Spacer(modifier = Modifier.size(16.dp))
                MinionButtonSet(
                    modifier = Modifier,
                    contentLeft = "삭제",
                    onClickLeft = onConfirm,
                    contentRight = "취소",
                    onClickRight = onDismiss
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SimpleDeleteDialogPreview(){
    SimpleDeleteDialog(
        onDismiss = {}
    ){}
}