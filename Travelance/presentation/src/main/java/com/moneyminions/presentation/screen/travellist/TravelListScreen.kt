package com.moneyminions.presentation.screen.travellist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.screen.travellist.view.TravelCardView
import com.moneyminions.presentation.theme.CardLightGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.viewmodel.travellist.TravelListViewModel

@Composable
fun TravelListScreen(
    travelListViewModel: TravelListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val modifier = Modifier
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Button(
            onClick = {},
            modifier = modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(CardLightGray),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "add travel",
                tint = PinkDarkest,
                modifier = modifier.padding(vertical = 16.dp),
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            content = {
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
