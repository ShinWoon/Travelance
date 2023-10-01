package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.GraphGray

@Composable
fun SettingItem(
    icon: Painter,
    color: Color,
    text: String,
    onClick: () -> Unit
){
    val constraintSet = ConstraintSet{
        val icon = createRefFor("icon")
        val text = createRefFor("text")
        val moveIcon = createRefFor("moveIcon")

        constrain(icon){
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(parent.start, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }
        constrain(text){
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(icon.end, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }
        constrain(moveIcon){
            top.linkTo(parent.top, margin = 8.dp)
            end.linkTo(parent.end, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = icon,
                tint = color,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .layoutId("icon")
            )
            Text(
                text = text,
                style = CustomTextStyle.pretendardSemiBold18,
                color = color,
                modifier = Modifier.layoutId("text")
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .layoutId("moveIcon")
                    .clickable {
                        onClick()
                    }
            )

        }
        Spacer(modifier = Modifier.size(16.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(GraphGray)
            .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFF)
@Composable
fun SettingItemPreview(){
//    SettingItem(icon = painterResource(id = R.drawable.ic_setting), text = "텍스트") {
//
//    }
}