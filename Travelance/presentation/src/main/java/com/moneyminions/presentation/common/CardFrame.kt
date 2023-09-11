package com.moneyminions.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.moneyminions.presentation.R

private val cardFrameList = listOf(
    R.drawable.ic_credit_card_1,
    R.drawable.ic_credit_card_2,
    R.drawable.ic_credit_card_3
)

@Composable
fun CardFrame(
    name: String,
    number: String,
    idx: Int,
    company: String,
    logo: String
){
    val imagePainter = painterResource(id = cardFrameList[idx])

    val constraintSet = ConstraintSet{
        val name = createRefFor("name")
        val number = createRefFor("number")
        val company = createRefFor("company")
        val logo = createRefFor("logo")

        constrain(name){
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
        }
        constrain(number){
            start.linkTo(parent.start, margin = 8.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)
        }
        constrain(company){
            end.linkTo(parent.end, margin = 8.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)
        }
        constrain(logo){
            end.linkTo(company.start, margin = 8.dp)
            bottom.linkTo(company.bottom)
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height)
    ) {
        Card(
            modifier = Modifier
                .background(
                    color = Color.Companion.Transparent,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null, // 이미지의 설명 (accessibility)을 여기서 설정할 수 있습니다.
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height),

                contentScale = ContentScale.Crop
            )
        }
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = name,
                style = CustomTextStyle.pretendardSemiBold16,
                color = Color.White,
                modifier = Modifier.layoutId("name")
            )
            Text(
                text = number,
                style = CustomTextStyle.pretendardBold20,
                color = Color.White,
                modifier = Modifier.layoutId("number")
            )
            Text(
                text = company,
                style = CustomTextStyle.pretendardSemiBold12,
                color = Color.White,
                modifier = Modifier.layoutId("company")
            )
            AsyncImage(
                model = logo,
                contentDescription = null,
                modifier = Modifier.layoutId("logo")
                    .size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardFramePreview(){
    CardFrame(name = "카드별칭", number = "1234567812345678", idx = 0, company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
}