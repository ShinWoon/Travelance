package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.Gray
import com.moneyminions.presentation.theme.PinkMiddle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHelpDialog(
    modifier: Modifier = Modifier,
    content: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(CardLightGray),
        ) {
            Column(
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        tint = DarkGray,
                        contentDescription = "help icon",
                        modifier = modifier.size(24.dp),
                        )
                    Spacer(modifier = modifier.width(8.dp))
                    Text(
                        text = content,
                        color = DarkerGray,
                        style = pretendardSemiBold14,
                        textAlign = TextAlign.Start,
                        lineHeight = 20.sp,
                        modifier = modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = modifier.height(8.dp))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "확인",
                        color = PinkMiddle,
                        style = pretendardBold14)
                }
            }
        }
    }
}