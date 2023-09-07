package com.moneyminions.presentation.screen.travellist.view

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.moneyminions.presentation.theme.CardLightGray

@Composable
fun TravelCardView(
    modifier: Modifier,
    travelName: String,
    travelStart: String,
    travelEnd: String,
    done: Boolean,
    moneyAmount: String,
    iconId : Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        ConstraintLayout(modifier = modifier) {
            val (travelNameTxt, travelIcon, travelDateTxt, travelState, travelMoneyTxt) = createRefs()
            Text(
                text = travelName,
                modifier = Modifier.constrainAs(travelNameTxt) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            )
//            Icon(painter =
//                when(iconId) {
//                       1 -> painterResource(id = )
//                       }, contentDescription = )
        }
    }
}
