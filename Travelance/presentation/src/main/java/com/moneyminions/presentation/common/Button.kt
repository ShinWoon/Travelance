package com.moneyminions.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold20
import com.moneyminions.presentation.theme.Gray
import com.moneyminions.presentation.theme.PinkDarkest

@Composable
fun MinionPrimaryButton(
    content: String,
    modifier: Modifier = Modifier,
    isEnabled: MutableState<Boolean> = mutableStateOf(true),
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = PinkDarkest,
            contentColor = White,
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled.value,
    ) {
        Text(text = content, style = pretendardBold20)
    }
}

@Composable
fun MinionButtonSet(
    modifier: Modifier,
    contentLeft: String,
    onClickLeft: () -> Unit,
    contentRight: String,
    onClickRight: () -> Unit,
) {
    Row(
        modifier = modifier,
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = { onClickLeft() },
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkDarkest,
                contentColor = White,
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = contentLeft, style = pretendardBold12)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = { onClickRight() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray,
                contentColor = White,
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = contentRight, style = pretendardBold12)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MinionButtonSetPreview() {
    MinionButtonSet(
        modifier = Modifier.fillMaxWidth(),
        contentLeft = "추가",
        contentRight = "취소",
        onClickLeft = {},
        onClickRight = {},
    )
}
