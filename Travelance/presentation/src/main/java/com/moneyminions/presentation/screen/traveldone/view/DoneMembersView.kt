package com.moneyminions.presentation.screen.traveldone.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moneyminions.domain.model.traveldone.RoomUserDto
import com.moneyminions.presentation.common.CustomTextStyle.pretendardSemiBold16
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.theme.DarkerGray

@Composable
fun DoneMembersView(
    memberInfoList: List<RoomUserDto>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize(),
        ) {
            itemsIndexed(memberInfoList) { index, item ->
                MemberInfoView(item)
            }
        }
    }
}

@Composable
fun MemberInfoView(
    roomUserDto: RoomUserDto,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MinionProfile(size = 96.dp, img = roomUserDto.profileUrl)
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = roomUserDto.travelNickname,
            color = DarkerGray,
            style = pretendardSemiBold16,
        )
    }
}
