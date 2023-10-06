package com.moneyminions.presentation.screen.traveldone.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold16
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkerGray

private const val TAG = "D210"

@Composable
fun DoneAnnouncementView(
    modifier: Modifier = Modifier,
    noticeAllInfo: List<NoticeAllDto>,
    clickedNotice: (NoticeAllDto) -> Unit,
    clickAction: () -> Unit,
) {
    Column {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (noticeAllInfo.isEmpty()) {
                item {
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(CardLightGray),
                    ) {
                        Text(
                            text = "공지가 없습니다",
                            color = DarkerGray,
                            style = CustomTextStyle.pretendardSemiBold16,
                            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            }
            itemsIndexed(noticeAllInfo) { index, item ->
                DoneAnnouncementCard(
                    noticeInfo = item,
                    clickedNotice = clickedNotice,
                    clickAction = clickAction,
                )
            }
        }
    }
}

@Composable
fun DoneAnnouncementCard(
    modifier: Modifier = Modifier,
    noticeInfo: NoticeAllDto,
    clickedNotice: (NoticeAllDto) -> Unit,
    clickAction: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(CardLightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
        ),
        modifier = modifier
            .size(height = 168.dp, width = 136.dp)
            .clickable(
                onClick = {
                    clickedNotice(noticeInfo)
                    Log.d(TAG, "DoneAnnouncementCard: clicked")
                    clickAction()
                },
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = noticeInfo.title,
                color = DarkerGray,
                style = pretendardBold16,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth(),
            )
            if (noticeInfo.link != "") {
                Icon(
                    painter = painterResource(id = R.drawable.ic_link),
                    modifier = modifier.size(24.dp),
                    contentDescription = "link icon",
                )
            }
        }
    }
}
