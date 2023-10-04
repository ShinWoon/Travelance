package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.presentation.theme.CardLightGray

@Composable
fun DoneAnnouncementView(
    modifier: Modifier = Modifier,
    noticeAllInfo: List<NoticeAllDto>,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(noticeAllInfo) { index, item ->
            DoneAnnouncementCard()
        }
    }
}

@Composable
fun DoneAnnouncementCard(
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(CardLightGray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
        ),
        modifier = modifier.size(height = 168.dp, width = 136.dp),
    ) {
    }
}
