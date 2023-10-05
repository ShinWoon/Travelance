package com.moneyminions.paybank.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithTitle(
    modifier: Modifier,
    title: String,
    hint: String?,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType? = KeyboardType.Text
){
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Black, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = Color.DarkGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
        placeholderColor = Color.Gray // 힌트 색상
    )
    var isHintVisible by remember { mutableStateOf(value.isEmpty()) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val onFocusChange: (Boolean) -> Unit = { isFocused ->
        if (!isFocused) {
            // 포커스가 없을 때 키보드를 닫습니다.
            Log.d("keyboardController", "$keyboardController ")
            keyboardController?.hide()
        }
    }



    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.layoutId("textTitle")
        )
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isHintVisible = it.isEmpty()
            },
            placeholder = {
                Text(
                    text = hint?:"",
                    color = Color.Gray
                )
            },
            singleLine = true,
            colors = outlinedTextFieldColors,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType!!
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // 키보드 닫기
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .layoutId("textField")
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it.isFocused) }
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TextFieldWithTitlePreview(){
    TextFieldWithTitle(modifier = Modifier, title = "title", hint = "hint", value = "", onValueChange = {})
}