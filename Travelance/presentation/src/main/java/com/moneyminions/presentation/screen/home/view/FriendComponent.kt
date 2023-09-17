package com.moneyminions.presentation.screen.home.view

import android.content.ActivityNotFoundException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.screen.travellist.util.clickable

@Composable
fun FriendComponent() {
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
            items(10) {
                MinionProfile(48.dp)
            }
        }
    }
}
