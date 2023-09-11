package com.moneyminions.presentation.screen.mypage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.viewpager2.widget.ViewPager2
import coil.compose.rememberAsyncImagePainter
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CardFrame
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLightest
import com.moneyminions.presentation.viewmodel.mypage.MyPageViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

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
