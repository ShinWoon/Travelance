package com.moneyminions.presentation.screen.travellist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.view.Calendar
import com.moneyminions.presentation.screen.travellist.view.DateTextComponent
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTravelScreen(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val createTravelConstraintSet = ConstraintSet {
        val travelNameField = createRefFor("travelNameField")

        constrain(travelNameField) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            navController = navController,
            title = "여행 생성"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                TextFieldWithTitle(
                    title = "이름",
                    hint = "여행 이름을 입력하세요",
                    value = createTravelViewModel.travelName.value,
                    onValueChange = {
                        createTravelViewModel.setTravelName(it)
                    }
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
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
            }
            Calendar()
            Column {
                DateTextComponent(
                    text = "시작 날짜 : ",
                    value = createTravelViewModel.startDate.value
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )
                DateTextComponent(
                    text = "종료 날짜 : ",
                    value = createTravelViewModel.endDate.value
                )
            }
            MinionPrimaryButton(
                content = "생성",
                modifier = Modifier.fillMaxWidth()
            ) {
                // homeScreen으로 이동 (방 생성 stack pop)
                navController.navigate(Screen.SubHome.route) {
                    popUpTo(Screen.TravelList.route)
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
