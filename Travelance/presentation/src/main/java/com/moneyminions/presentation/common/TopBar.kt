package com.moneyminions.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.theme.LightGray

@Composable
fun TopBar(
    navController: NavHostController,
    title: String
){
    val constraintSet = ConstraintSet{
        val titleText = createRefFor("titleText")
        val backButton = createRefFor("backButton")
        val divider = createRefFor("divider")

        constrain(titleText){
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 16.dp)
        }
        constrain(backButton){
            top.linkTo(parent.top)
            start.linkTo(parent.start, margin = 16.dp)
            bottom.linkTo(parent.bottom)
        }
        constrain(divider){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }

    ConstraintLayout(
        constraintSet,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "backButton",
            modifier = Modifier.layoutId("backButton")
                .clickable{
                    navController.popBackStack()
                }
        )
        Text(
            text = title,
            style = CustomTextStyle.pretendardBoldBlack20,
            modifier = Modifier.layoutId("titleText")
        )
        Divider(
            color = LightGray,
            thickness = 2.dp,
            modifier = Modifier.layoutId("divider")
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TopBarPreview(){
    TopBar(
        navController = rememberNavController(),
        title = "title"
    )
}