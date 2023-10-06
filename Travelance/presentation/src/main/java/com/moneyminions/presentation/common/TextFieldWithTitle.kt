package com.moneyminions.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithTitle(
    title: String,
    hint: String?,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType? = KeyboardType.Text,
    maxLine: Int = 200,
) {
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
    )
    var isHintVisible by remember { mutableStateOf(value.isEmpty()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = CustomTextStyle.pretendardBold16,
            modifier = Modifier.layoutId("textTitle"),
        )
        Spacer(
            modifier = Modifier.size(8.dp),
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isHintVisible = it.isEmpty()
            },
            placeholder = {
                Text(
                    text = hint ?: "",
                    style = CustomTextStyle.pretendardRegular12,
                    color = GraphGray,
                )
            },
            singleLine = true,
            colors = outlinedTextFieldColors,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType!!,
            ),
            maxLines = maxLine,
            modifier = Modifier
                .layoutId("textField")
                .fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TextFieldWithTitlePreview() {
    TextFieldWithTitle(title = "title", hint = "hint", value = "", onValueChange = {})
}
