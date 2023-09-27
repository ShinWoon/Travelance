package com.moneyminions.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "AuthenticationDialog D210"
@Composable
fun AuthenticationDialog(
    navController: NavHostController,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    type: String,
    value: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit
){
    if(showDialog){
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false, // 뒤로 가기 버튼으로 닫기 방지
                dismissOnClickOutside = false // 다이얼로그 바깥 클릭으로 닫기 방지
            )
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
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        decorationBox = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                value.forEachIndexed { index, char ->
                                    TextFieldCharContainer(
                                        char = char,
                                        isFocused = index == value.lastIndex
                                    )
                                }
                                repeat(4 - value.length) {
                                    TextFieldCharContainer(
                                        char = ' ',
                                        isFocused = false,
                                    )
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    MinionButtonSet(
                        modifier = Modifier.width(250.dp),
                        contentLeft = "확인",
                        onClickLeft = {
                            //일치여부 확인 후 이동
                            Log.d(TAG, "result : $value")
                            onConfirm()
                        },
                        contentRight = "취소",
                        onClickRight = {
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }

}

@Composable
private fun TextFieldCharContainer(
    modifier: Modifier = Modifier,
    char: Char,
    isFocused: Boolean
){
    Box(
        modifier = modifier
            .size(50.dp)
            .background(
                color = PinkLightest,
                shape = RoundedCornerShape(8.dp),
            )
            .run {
                if (isFocused) {
                    border(
                        width = 1.dp,
                        color = PinkDarkest,
                        shape = RoundedCornerShape(8.dp),
                    )
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = char.toString())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AuthenticationDialogPreview(){
    AuthenticationDialog(
        navController = rememberNavController(),
        showDialog = true, onDismiss = { } , type = "account",
        value = "", onValueChange = {}, onConfirm = {}
    )
}