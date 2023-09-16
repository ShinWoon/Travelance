package com.moneyminions.presentation.screen.travellist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle.pretendardBold14
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.view.TravelCardView
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.viewmodel.travellist.TravelListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelListScreen(
    travelListViewModel: TravelListViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "방 생성",
                        color = DarkGray,
                        style = pretendardBold14
                    )
                },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    tint = PinkDarkest,
                    contentDescription = "room add icon") },
                containerColor = CardLightGray,
                onClick = {
                    navController.navigate(Screen.CreateTravel.route)
                })
        }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 24.dp, 16.dp, 0.dp),
            content = {
                item {
                    TravelCardView(
                        modifier = modifier,
                        travelName = "룰루랄라",
                        travelStart = "2023.07.23",
                        travelEnd = "2023.07.30",
                        done = "yet",
                        moneyAmount = 5500000,
                        iconId = R.drawable.ic_camera,
                    )
                }
                item {
                    TravelCardView(
                        modifier = modifier,
                        travelName = "룰루랄라",
                        travelStart = "2023.07.23",
                        travelEnd = "2023.07.30",
                        done = "doing",
                        moneyAmount = 5500000,
                        iconId = R.drawable.ic_camera,
                    )
                }
                items(10) {
                    TravelCardView(
                        modifier = modifier,
                        travelName = "룰루랄라",
                        travelStart = "2023.07.23",
                        travelEnd = "2023.07.30",
                        done = "done",
                        moneyAmount = 5500000,
                        iconId = R.drawable.ic_camera,
                    )
                }
            },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TravelListScreenPreview() {
    TravelListScreen(navController = rememberNavController())
}
