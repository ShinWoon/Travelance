package com.moneyminions.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.screen.travellist.util.clickable

@Composable
fun TopBar(
    navController: NavHostController,
    title: String,
    res: Int? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.size(16.dp))
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "backButton",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                style = CustomTextStyle.pretendardBold20,
            )
            Spacer(modifier = Modifier.size(4.dp))
            if(res!=null) {
                Image(
                    painter = painterResource(id = res),
                    contentDescription = "add button",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Divider(
            color = DividerDefaults.color,
            thickness = DividerDefaults.Thickness
        )
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TopBarPreview() {
    TopBar(
        navController = rememberNavController(),
        title = "title",
    )
}
