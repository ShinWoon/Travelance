@file:OptIn(ExperimentalMaterial3Api::class)

package com.moneyminions.presentation.screen.announcement

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.White


private const val TAG = "AnnouncementScreen_D210"
@Composable
fun AnnouncementScreen(
    navController: NavHostController,
) {
    Log.d(TAG, "AnnouncementScreen: on")
    var openAnnouncementWritingDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, title = "필독")
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAnnouncementWritingDialog = true
                },
                Modifier.padding(4.dp),
                containerColor = White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_add),
                    contentDescription = "add document"
                )
            }
        }
    
    
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(5) {
                    // todo API 통신시 수정 필요
                    AnnounceItem(
                        title = "숙소",
                        content = "펜션 이름 : 특화 펜션\n" +
                                "기간 : 2023/09/06 ~ 2023/09/08(2박 3일)\n" +
                                "비용 : 256,000원\n" +
                                "현장 결제 : 20,000원\n" +
                                "체크인 : 15시\n" +
                                "체크아웃 : 11시",
                        link = ""
                    )
                }
            }
        }
        
        if (openAnnouncementWritingDialog) {
            AnnouncementWritingDialog(onDismiss = { openAnnouncementWritingDialog = false })
        }
    }
    
}

@Composable
fun AnnounceItem(
    title: String,
    content: String,
    link: String,
) {
    
    // false: 제목만 보이는 상태 / true: 상세 보기 상태
    var isDetailInfo by remember { mutableStateOf(false) }
    var isIconStatus by remember { mutableStateOf(R.drawable.ic_down_navigation) }
    
    Card(
        modifier = Modifier
            .background(
                color = CardLightGray,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = CustomTextStyle.pretendardBold20)
                Image(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = "link",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            /**
                             * 작성한 URL로 이동 웹뷰 하던가 아님 브라우저에서 띄우던가..
                             */
                        }
                )
            }
            
            if (isDetailInfo) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = content, style = CustomTextStyle.pretendardSemiBold12)
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        isDetailInfo = !isDetailInfo
                    }
                ) {
                    Icon(
                        painter = painterResource(id = isIconStatus),
                        contentDescription = "move detail",
                    )
                }
            }
        }
        
        isIconStatus =
            if (isDetailInfo) R.drawable.ic_up_navigation else R.drawable.ic_down_navigation
        
    }
}