package com.moneyminions.presentation.screen.travellist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.view.Calendar
import com.moneyminions.presentation.screen.travellist.view.DateTextComponent
import com.moneyminions.presentation.screen.travellist.view.ProfileDialog
import com.moneyminions.presentation.utils.MoneyUtils
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.utils.addFocusCleaner
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.travel.TravelEditViewModel
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel
import kotlinx.coroutines.launch

private const val TAG = "CreateTravelScreen_D210"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTravelScreen(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    travelEditViewModel: TravelEditViewModel = hiltViewModel(),
    navController: NavHostController,
    mainViewModel: MainViewModel,
    roomId: Int = -1
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val travelRoomCreate by createTravelViewModel.createTravelRoomResult.collectAsState()
    NetworkResultHandler(
        state = travelRoomCreate,
        errorAction = {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("방 생성 실패")
            }
        },
        successAction = {
            // homeScreen으로 이동 (방 생성 stack pop)
            Log.d(TAG, "CreateTravelScreen: $it")
            mainViewModel.setSelectRoomId(it.result.toInt())
            navController.navigate(Screen.SubHome.route) {
                popUpTo(Screen.TravelList.route)
            }
        },
    )

    val createTravelConstraintSet = ConstraintSet {
        val travelNameField = createRefFor("travelNameField")

        constrain(travelNameField) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    }

    var travelRoomInfo by remember { mutableStateOf(TravelRoomInfoDto()) }
    if(roomId != -1) {
        LaunchedEffect(Unit) {
            travelRoomInfo = mainViewModel.travelRoomInfo
        }
    }

    val travelRoomEditState by travelEditViewModel.travelRoomEditState.collectAsState()
    NetworkResultHandler(state = travelRoomEditState, errorAction = { /*TODO*/ }, successAction = {
        navController.popBackStack()
    })

    // 프로필 설정 다이얼로그
    var openProfileDialog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .addFocusCleaner(
                    LocalFocusManager.current
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, bottom = 8.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    TextFieldWithTitle(
                        title = "이름",
                        hint = "여행 이름을 입력하세요",
                        value = if(roomId == -1) createTravelViewModel.travelName.value else travelRoomInfo.travelName,
                        onValueChange = {
                            createTravelViewModel.setTravelName(it)
                        },
                    )
                    Spacer(
                        modifier = Modifier.size(16.dp),
                    )
                    TextFieldWithTitle(
                        title = "예산",
                        hint = "예산을 입력해주세요",
                        value = if(roomId == -1) createTravelViewModel.travelBudget.value else travelRoomInfo.budget.toString(),
                        onValueChange = {
                            createTravelViewModel.setTravelBudget(it)
                        },
                        keyboardType = KeyboardType.Number,
                    )
                }
                Calendar()
                Column {
                    DateTextComponent(
                        text = "시작 날짜 : ",
                        value = if(roomId == -1) createTravelViewModel.startDate.value else travelRoomInfo.startDate,
                    )
                    Spacer(
                        modifier = Modifier.size(16.dp),
                    )
                    DateTextComponent(
                        text = "종료 날짜 : ",
                        value = if(roomId == -1) createTravelViewModel.endDate.value else travelRoomInfo.endDate,
                    )
                }
                MinionPrimaryButton(
                    content = "생성",
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    when(roomId) {
                        -1 -> {
                            if (createTravelViewModel.InputTravelCheck()) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("입력을 확인하세요.")
                                }
                            } else {
                                openProfileDialog = true
//                        createTravelViewModel.createTravelRoom()
                            }
                        }
                        else -> {
                            travelEditViewModel.editTravelRoomInfo(roomId = roomId, travelRoomInfoDto = travelRoomInfo)
                        }
                    }

                }
            }
        }
    }

    // 프로필 설정 다이얼로그
    if(openProfileDialog) {
        ProfileDialog(onDismiss = {openProfileDialog = false})
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
//@Composable
//fun CreateTravelScreenPreview() {
//    CreateTravelScreen(navController = rememberNavController())
//}
