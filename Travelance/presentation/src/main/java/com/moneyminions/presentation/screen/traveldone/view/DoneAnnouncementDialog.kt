package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold14
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkerGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoneAnnouncementDialog(
    noticeInfo: NoticeAllDto,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    linkClick: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismissRequest)
    {
        Card(
            colors = CardDefaults.cardColors(CardLightGray)
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = noticeInfo.title,
                    color = DarkerGray,
                    style = pretendardBold16,
                    textAlign = TextAlign.Start,
                    modifier = modifier.fillMaxWidth(),
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = noticeInfo.content,
                    color = DarkerGray,
                    style = pretendardSemiBold14,
                    textAlign = TextAlign.Start,
                    modifier = modifier.fillMaxWidth(),
                )
                Spacer(modifier = modifier.height(32.dp))
                IconButton(onClick = { linkClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_link),
                        modifier = modifier.size(24.dp),
                        contentDescription = "link icon"
                    )
                }
            }
        }
    }
}