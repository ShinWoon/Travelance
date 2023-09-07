package com.moneyminions.presentation.screen.travellist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.viewmodel.travellist.TravelListViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun TravelListScreen(
    travelListViewModel: TravelListViewModel = hiltViewModel(),
    navController: NavHostController
){


    val constraintSet = ConstraintSet{
        val addButton = createRefFor("addButton")
        val travelListLazyColumn = createRefFor("travelListLazyColumn")

        constrain(addButton){
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
        constrain(travelListLazyColumn){
        }
    }

    ConstraintLayout(
        constraintSet,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("addButton")
        ){
            Text(
                text = "+"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TravelListScreenPreview(){
    TravelListScreen(navController = rememberNavController())
}