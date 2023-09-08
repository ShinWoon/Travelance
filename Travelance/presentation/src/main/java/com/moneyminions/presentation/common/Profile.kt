package com.moneyminions.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.moneyminions.presentation.R
import com.moneyminions.presentation.theme.PinkDarkest

/**
 * todo domain DTO 생성 후 파라미터 처리 해야함
 */
@Composable
fun MinionProfile() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg")
            .error(R.drawable.ic_profile_person)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_profile_person), // 이미지가 없을때 넣을 것
        contentScale = ContentScale.Crop, // 사이즈에 맞지 않은 것은 잘라냄
        contentDescription = "profile",
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(PinkDarkest)// 둥굴게,
    )
}


@Preview(showBackground = true)
@Composable
fun MinionProfilePreview() {
    MinionProfile()
}