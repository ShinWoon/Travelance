package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.CategoryExpenseDto
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.DarkerGray

@Composable
fun DoneTotalView(
    modifier: Modifier = Modifier,
    noticeAllInfo: List<NoticeAllDto>,
    categoryExpenseList: List<CategoryExpenseDto>,
    clickedNotice: (NoticeAllDto) -> Unit,
    clickAction: () -> Unit,
    moveToMap: () -> Unit,
    showMap: @Composable () -> Unit,
) {
    val scrollableState = rememberScrollState()
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DoneTotalComponent(text = "사용 카테고리", categoryExpenseList = categoryExpenseList)
            DoneTotalComponent(text = "여행 공지", noticeAllInfo = noticeAllInfo, clickAction = clickAction, clickedNotice = clickedNotice)
            DoneTotalComponent(text = "여행 발자취", moveToMap = moveToMap, showMap = showMap)
        }
    }
}

@Composable
fun DoneTotalComponent(
    text: String,
    modifier: Modifier = Modifier,
    noticeAllInfo: List<NoticeAllDto> = listOf(),
    categoryExpenseList: List<CategoryExpenseDto> = listOf(),
    clickedNotice: (NoticeAllDto) -> Unit = {},
    clickAction: () -> Unit = {},
    moveToMap: () -> Unit = {},
    showMap: @Composable () -> Unit = {},
) {
    Column {
        DoneTotalCommonTitle(text = text)
        Spacer(modifier = modifier.height(12.dp))
        when (text) {
            "사용 카테고리" -> CategoryGraphView(categoryExpenseList = categoryExpenseList)
            "여행 공지" -> DoneAnnouncementView(noticeAllInfo = noticeAllInfo, clickAction = clickAction, clickedNotice = clickedNotice)
            "여행 발자취" -> DoneMapView(moveToMap = moveToMap, showMap = showMap)
            else -> {}
        }
        Spacer(modifier = modifier.height(36.dp))
    }
}

@Composable
fun DoneTotalCommonTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = DarkerGray,
        style = CustomTextStyle.pretendardBold16,
        textAlign = TextAlign.Start,
        modifier = modifier.fillMaxWidth(),
    )
}
