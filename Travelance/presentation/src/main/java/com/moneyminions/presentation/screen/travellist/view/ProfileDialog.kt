package com.moneyminions.presentation.screen.travellist.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.MinionProfile
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.utils.UploadUtils
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel

private const val TAG = "ProfileDialog_D210"
@Composable
fun ProfileDialog(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    onDismiss: () -> Unit,
    roomId: Int = 0,
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // 여행방 생성 (성공시 룸ID 반환 / 실패시 0 반환)
    val joinTravelRoomState by createTravelViewModel.joinTravelRoomResult.collectAsState()
    NetworkResultHandler(
        state = joinTravelRoomState,
        errorAction = {
            Log.d(TAG, "ProfileDialog: 서버 오류")
        },
        successAction = {
            Log.d(TAG, "ProfileDialog 초대받은 사용자 참가 성공 : $it ")
            // 정상적으로 방이 생성되었다면... 다이얼로그 off
            mainViewModel.setSelectRoomId(roomId)
            mainViewModel.setInviteRoomId(0)
            navController.navigate(Screen.SubHome.route)
        }
    )

    Dialog(
      onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(24.dp, 32.dp, 24.dp, 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(roomId != 0) {
                    Text(text = "여행에 참가하세요!", style = CustomTextStyle.pretendardBold20,)
                    Spacer(modifier = Modifier.height(32.dp))
                }

                ProfileImage(createTravelViewModel)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                TextFieldWithTitle(
                    title = "닉네임",
                    hint = "닉네임을 입력해주세요.",
                    value = createTravelViewModel.nickName.value,
                    onValueChange = {
                        createTravelViewModel.setNickName(it)
                    }
                )
    
                Spacer(modifier = Modifier.height(32.dp))
                
                MinionPrimaryButton(
                    content = "완료",
                    modifier = Modifier,
                    onClick = {
                        // todo API 호출 방 생성 + 프로필 정보
                        if(!createTravelViewModel.InputProfileCheck()) {
                            Log.d(TAG, "ProfileDialog: 여행방 등록 메서드 call")
                            // 이미지 url -> file로
                            Log.d(TAG, " -> ProfileDialog url: ${createTravelViewModel.profileImage.value} ")
                            val imageFile = UploadUtils.createMultipartFromUri(context, "imageFiles", createTravelViewModel.profileImage.value)
                            if (imageFile != null) {
                                Log.d(TAG, " -> ProfileDialog imageFile: ${imageFile.body}")
                            }
                            Log.d(TAG, "ProfileDialog: 룸 생성 체크: $roomId")
                            if(roomId == 0) {
                                createTravelViewModel.createTravelRoom(imageFile)
                            } else {
                                // 여행방 참가
                                createTravelViewModel.joinTravelRoom(
                                    roomId = roomId,
                                    imageFiles = imageFile
                                )
                            }
                        }
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileImage(
    createTravelViewModel: CreateTravelViewModel
) {
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        Log.d(TAG, "First Select ProfileImage: $uri")
        createTravelViewModel.setProfileImage(uri.toString())
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MinionProfile(size = 120.dp, createTravelViewModel.profileImage.value)
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = "사진 추가",
            color = PinkDarkest,
            style = CustomTextStyle.pretendardBold12,
            modifier = Modifier.clickable {
                // todo : 사진 추가하기
                launcher.launch("image/*")
            }
        )
    }
}