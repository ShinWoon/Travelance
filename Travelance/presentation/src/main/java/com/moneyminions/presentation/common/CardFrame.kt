package com.moneyminions.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    }
}

@Preview(showBackground = true)
@Composable
fun CardFramePreview(){
    CardFrame(name = "카드별칭", number = "1234567812345678", idx = 0, company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png")
}