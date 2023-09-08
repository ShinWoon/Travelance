package com.moneyminions.presentation.screen.travellist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel

@Composable
fun CreateTravelScreen(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    navController: NavHostController
){
    val createTravelConstraintSet = ConstraintSet{
        val travelNameField = createRefFor("travelNameField")

        constrain(travelNameField){
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopBar(
            navController = navController,
            title = "여행 생성"
        )
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        Column (

        ){
            TextFieldWithTitle(
                title = "이름",
                hint = "여행 이름을 입력하세요",
                value = createTravelViewModel.travelName.value,
                onValueChange = {
                    createTravelViewModel.setTravelName(it)
                }
            )
        }
        Column (

        ){
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
        Spacer(
            modifier = Modifier.size(16.dp)
        )
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CreateTravelScreenPreview(){
    CreateTravelScreen(navController = rememberNavController())
}