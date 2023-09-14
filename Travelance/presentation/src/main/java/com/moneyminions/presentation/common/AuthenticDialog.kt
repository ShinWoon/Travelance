package com.moneyminions.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkLightest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    type: String,
    value1: String,
    onValue1Change: (String) -> Unit,
    value2: String,
    onValue2Change: (String) -> Unit,
    value3: String,
    onValue3Change: (String) -> Unit,
    value4: String,
    onValue4Change: (String) -> Unit,
    value: String //최종적인 조합 문자열
){
    if(showDialog){
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp)), // 모서리를 둥글게 만들기
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    if (type == "account") {
                        Text(
                            text = "1원 인증",
                            style = CustomTextStyle.pretendardBold16
                        )
                        Spacer(modifier = Modifier.size(32.dp))
                        Text(
                            text = "입금 내역에 있는 단어를 입력해주세요",
                            style = CustomTextStyle.pretendardMedium12,
                            color = DarkGray
                        )
                    }
                    if (type == "password") {
                        Text(
                            text = "비밀번호",
                            style = CustomTextStyle.pretendardBold16
                        )
                        Spacer(modifier = Modifier.size(32.dp))
                        Text(
                            text = "비밀번호 4자리를 입력해주세요",
                            style = CustomTextStyle.pretendardMedium12,
                            color = DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = value1,
                            onValueChange = {
                                onValue1Change(it)
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = PinkLightest,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(50.dp)
                        )
                        OutlinedTextField(
                            value = value2,
                            onValueChange = {
                                onValue2Change(it)
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = PinkLightest,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(50.dp)
                        )
                        OutlinedTextField(
                            value = value3,
                            onValueChange = {
                                onValue3Change(it)
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = PinkLightest,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(50.dp)
                        )
                        OutlinedTextField(
                            value = value4,
                            onValueChange = {
                                onValue4Change(it)
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = PinkLightest,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(32.dp))
                    MinionButtonSet(
                        modifier = Modifier.width(250.dp),
                        contentLeft = "확인",
                        onClickLeft = {

                        },
                        contentRight = "취소",
                        onClickRight = {

                        }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }

}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AuthenticDialogPreview(){
    AuthenticDialog(showDialog = true, onDismiss = { } , type = "account",
        value1 = "", onValue1Change = {},
        value2 = "", onValue2Change = {},
        value3 = "", onValue3Change = {},
        value4 = "", onValue4Change = {}, "")
}