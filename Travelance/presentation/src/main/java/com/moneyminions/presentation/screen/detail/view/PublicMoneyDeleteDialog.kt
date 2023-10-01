package com.moneyminions.presentation.screen.detail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold12
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.DarkerGray
import com.moneyminions.presentation.theme.PinkDarkest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicMoneyDeleteDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    acceptRequest: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            colors = CardDefaults.cardColors(CardLightGray)
        ) {
            Column(
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "공금 내역에서 삭제하실 건가요?",
                    color = DarkerGray,
                    style = pretendardSemiBold14,
                    textAlign = TextAlign.Start,
                    modifier = modifier.fillMaxWidth(),
                )
                Spacer(modifier = modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(
                            text = "취소",
                            color = DarkGray,
                            style = pretendardBold12,
                        )
                    }
                    Spacer(modifier = modifier.width(16.dp))
                    TextButton(onClick = acceptRequest) {
                        Text(
                            text = "삭제",
                            color = PinkDarkest,
                            style = pretendardBold12,
                        )
                    }
                }
            }
        }
    }
}