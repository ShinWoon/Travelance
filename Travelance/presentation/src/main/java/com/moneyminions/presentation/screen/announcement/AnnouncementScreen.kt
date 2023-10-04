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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.domain.model.home.AnnouncementDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.FloatingButtonColor
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.announcement.AnnouncementViewModel

private const val TAG = "AnnouncementScreen_D210"
@Composable
fun AnnouncementScreen(
    navController: NavHostController,
    roomId: Int,
    announcementViewModel: AnnouncementViewModel = hiltViewModel(),
) {
    // 공지사항 리스트 GET
    LaunchedEffect(Unit) {
        announcementViewModel.getAnnouncementList(roomId)
    }
    
    // 공지사항 저장
    val saveAnnouncementState by announcementViewModel.saveAnnouncementResult.collectAsState()
    NetworkResultHandler(
        state = saveAnnouncementState,
        errorAction = { /*TODO*/ },
        successAction = {
            Log.d(TAG, "AnnouncementWritingDialog: 공지사항 등록 성공 $it")
            announcementViewModel.getAnnouncementList(roomId)
        }
    )
    
    // 공지사항 리스트
    val getAnnouncementListState by announcementViewModel.getAnnouncementListResult.collectAsState()
    NetworkResultHandler(
        state = getAnnouncementListState,
        errorAction = { /*TODO*/ },
        successAction = {
            Log.d(TAG, "AnnouncementScreen: 전체 공지 사항 Get 성공 \n $it")
            announcementViewModel.refleshAnnouncementList(it.toMutableList())
        }
    )
    
    // 공지사항 삭제
    val deleteAnnouncementState by announcementViewModel.deleteAnnouncementResult.collectAsState()
    NetworkResultHandler(
        state = deleteAnnouncementState,
        errorAction = { /*TODO*/ },
        successAction = {
            Log.d(TAG, "AnnouncementScreen: 전체 공지 사항 삭제 성공 \n $it")
            announcementViewModel.getAnnouncementList(roomId)
        }
    )
    
    // 공지사항 수정
    val editAnnouncementState by announcementViewModel.editAnnouncementResult.collectAsState()
    NetworkResultHandler(
        state = editAnnouncementState,
        errorAction = { /*TODO*/ },
        successAction = {
            Log.d(TAG, "AnnouncementScreen: 전체 공지 사항 수정 성공 \n $it")
            announcementViewModel.getAnnouncementList(roomId)
        }
    )
    
    Log.d(TAG, "AnnouncementScreen: $roomId \n")
    var openAnnouncementWritingDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAnnouncementWritingDialog = true
                },
                Modifier.padding(4.dp),
                containerColor = FloatingButtonColor,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_add),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "add document",
                )
            }
        },
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
                items(announcementViewModel.announcementList.value.size) {
                    // todo API 통신시 수정 필요
                    AnnounceItem(
                        navController = navController,
                        announcementViewModel = announcementViewModel,
                        roomId = roomId,
                        announcementViewModel.announcementList.value[it],
                    )
                }
            }
        }

        if (openAnnouncementWritingDialog) {
            AnnouncementWritingDialog(
                onDismiss = { openAnnouncementWritingDialog = false },
                roomId = roomId,
                announcementViewModel = announcementViewModel,
            )
        }
    }
}


@Composable
fun AnnounceItem(
    navController: NavHostController,
    announcementViewModel : AnnouncementViewModel,
    roomId: Int,
    announcementDto: AnnouncementDto,
) {
    // false: 제목만 보이는 상태 / true: 상세 보기 상태
    var isDetailInfo by remember { mutableStateOf(false) }
    var isIconStatus by remember { mutableStateOf(R.drawable.ic_down_navigation) }
    
    
    Card(
        colors = CardDefaults.cardColors(CardLightGray),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 0.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleWithLink(
                    navController = navController,
                    announcementDto = announcementDto
                )
                EditDropdownMenu(
                    announcementViewModel = announcementViewModel,
                    roomId = roomId,
                    noticeId = announcementDto.id,
                    announcementDto = announcementDto,
                )
            }
            

            if (isDetailInfo) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = announcementDto.content, style = CustomTextStyle.pretendardSemiBold12)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
                contentAlignment = Alignment.Center,
            ) {
                IconButton(
                    onClick = {
                        isDetailInfo = !isDetailInfo
                    },
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

@Composable
fun TitleWithLink(
    navController: NavHostController,
    announcementDto: AnnouncementDto,
) {
    
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = announcementDto.title, style = CustomTextStyle.pretendardBold20)
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_link),
            contentDescription = "link",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    // WebView
                    val url = announcementDto.link
                    Log.d(TAG, "Announcement Screen: 웹뷰 호출 (1) => $url")
                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "data", value = url)
                    navController.navigate(Screen.WebView.route)
                },
        )
    }
}


@Composable
fun EditDropdownMenu(
    announcementViewModel : AnnouncementViewModel,
    roomId: Int,
    noticeId: Int,
    announcementDto: AnnouncementDto,
) {
    // dropDownMenu
    var expandStatus by remember { mutableStateOf(false) }
    var openAnnouncementWritingDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.background(CardLightGray),
    ) {
        IconButton(
            onClick = {
                expandStatus = true
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_three_dot),
                contentDescription = "option"
            )
        }

        DropdownMenu(
            modifier = Modifier.background(CardLightGray),
            expanded = expandStatus,
            onDismissRequest = { expandStatus = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "수정") },
                onClick = {
                    announcementViewModel.apply {
                        setTitle(announcementDto.title)
                        setContent(announcementDto.content)
                        setLink(announcementDto.link)
                        setSelectNoticeId(noticeId)
                    }
                    openAnnouncementWritingDialog = true
                    expandStatus = false
                }
            )

            DropdownMenuItem(
                text = {Text(text = "삭제")},
                onClick = {
                    announcementViewModel.deleteAnnouncement(
                        roomId = roomId,
                        noticeId = noticeId
                    )
                    expandStatus = false
                }
            )
        }
    }
    
    if (openAnnouncementWritingDialog) {
        AnnouncementWritingDialog(
            onDismiss = { openAnnouncementWritingDialog = false },
            roomId = roomId,
            announcementViewModel = announcementViewModel,
        )
    }
}
