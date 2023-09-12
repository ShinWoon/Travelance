package com.moneyminions.presentation.screen.mypage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest

@Composable
fun AccountItem(
    logo: String,
    name: String,
    number: String,
){
    val constraintSet = ConstraintSet{
        val logo = createRefFor("logo")
        val name = createRefFor("name")
        val number = createRefFor("number")
        val delete = createRefFor("delete")

        constrain(logo){
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }
        constrain(name){
            top.linkTo(logo.top)
            start.linkTo(logo.end, margin = 8.dp)
            bottom.linkTo(number.top)
        }
        constrain(number){
            top.linkTo(name.bottom)
            start.linkTo(name.start)
            bottom.linkTo(logo.bottom)
        }
        constrain(delete){
            top.linkTo(parent.top)
            end.linkTo(parent.end, margin = 8.dp)
            bottom.linkTo(parent.bottom)
        }
    }
    Column(

    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = logo,
                contentDescription = null,
                modifier = Modifier
                    .layoutId("logo")
                    .size(30.dp)
            )
            Text(
                text = name,
                style = CustomTextStyle.pretendardMedium12,
                modifier = Modifier.layoutId("name")
            )
            Text(
                text = number,
                style = CustomTextStyle.pretendardMedium12,
                modifier = Modifier.layoutId("number")
            )
            Text(
                text = "삭제",
                style = CustomTextStyle.pretendardMedium12,
                color = PinkDarkest,
                modifier = Modifier.layoutId("delete")
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Divider(modifier = Modifier.fillMaxWidth()
            .height(1.dp)
            .background(GraphGray)
            .padding(horizontal = 16.dp)
        )
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountItemPreview(){
    AccountItem(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    )
}