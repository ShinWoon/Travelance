package com.moneyminions.presentation.screen.mypage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CardFrame
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.viewmodel.mypage.MyPageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageScreeen(
    navController: NavHostController,
    myPageViewModel: MyPageViewModel = hiltViewModel()
){
//    val pagerState = rememberPagerState(pageCount = {
//        myPageViewModel.cardList.size
//    })

    var scrollState by remember { mutableStateOf(0f) }
    var selectedItem by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "D210님!", //여기 유저의 닉네임이 들어가야함
                style = CustomTextStyle.pretendardBold24
            )
            Image(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = "setting",
                modifier = Modifier.size(30.dp)
                    .clickable {
                        navController.navigate(Screen.Setting.route)
                    }
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "카드 목록",
            style = CustomTextStyle.pretendardSemiBold12
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyRow(
            modifier = Modifier.height(200.dp)
        ){
            items(myPageViewModel.cardList){
                CardFrame(
                    name = it.name,
                    number = it.number,
                    idx = it.idx,
                    company = it.company,
                    logo = it.logo
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
//        HorizontalPager(
//            state = pagerState,
//            pageSpacing = 16.dp,
//            beyondBoundsPageCount = 2,
//            modifier = Modifier.fillMaxSize()
//        ) { idx ->
//            val card = myPageViewModel.cardList[idx]
//            Box(modifier = Modifier.fillMaxSize()) {
//                // Contains Image and Text composables
//                CardFrame(
//                    name = card.name,
//                    number = card.number,
//                    idx = card.idx,
//                    company = card.company,
//                    logo = card.logo
//                )
//            }
//        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun MyPageScreenPreview(){
    MyPageScreeen(navController = rememberNavController())
}
