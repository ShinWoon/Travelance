package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.viewmodel.home.HomeViewModel


private const val TAG = "FriendComponent"
@Composable
fun FriendComponent(
    homeViewModel: HomeViewModel
) {
    var context = LocalContext.current
    val friendInfoList = homeViewModel.travelRoomFriendInfo.value
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "친구들",
                style = CustomTextStyle.pretendardBold16
            )

            Text(
                text = "친구 추가",
                style = CustomTextStyle.pretendardLight12,
                modifier = Modifier.clickable {
                    homeViewModel.sendKakaoLink(context)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        /**
         * todo 사용자 정보로 처리해야함
         */
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(friendInfoList.size) {
                // todo 사진 못하면 이름이라도 넣어야 할듯,.....
                MinionProfile(size = 48.dp, img = friendInfoList[it].profileUrl)
            }
        }
    }
}



