package com.moneyminions.presentation.screen.travellist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.view.Calendar
import com.moneyminions.presentation.screen.travellist.view.DateTextComponent
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel
import kotlinx.coroutines.launch

private const val TAG = "CreateTravelScreen_D210"
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTravelScreen(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    navController: NavHostController,
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
            navController.navigate(Screen.SubHome.route) {
                popUpTo(Screen.TravelList.route)
            }
        }
    )
    
    val createTravelConstraintSet = ConstraintSet {
        val travelNameField = createRefFor("travelNameField")
        
        constrain(travelNameField) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TopBar(
                navController = navController,
                title = "여행 생성"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                TextFieldWithTitle(
                    title = "이름",
                    hint = "여행 이름을 입력하세요",
                    value = createTravelViewModel.travelName.value,
                    onValueChange = {
                        createTravelViewModel.setTravelName(it)
                    }
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                TextFieldWithTitle(
                    title = "예산",
                    hint = "예산을 입력해주세요",
                    value = createTravelViewModel.travelBudget.value,
                    onValueChange = {
                        createTravelViewModel.setTravelBudget(it)
                    },
                    keyboardType = KeyboardType.Number
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Calendar()
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                DateTextComponent(
                    text = "시작 날짜 : ",
                    value = createTravelViewModel.startDate.value
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                DateTextComponent(
                    text = "종료 날짜 : ",
                    value = createTravelViewModel.endDate.value
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                MinionPrimaryButton(
                    content = "생성",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (createTravelViewModel.InputCheck()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("입력을 확인하세요.")
                        }
                    } else {
                        createTravelViewModel.createTravelRoom()
                    }
                }
            }
            
        }
    }
    
    
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CreateTravelScreenPreview() {
    CreateTravelScreen(navController = rememberNavController())
}
