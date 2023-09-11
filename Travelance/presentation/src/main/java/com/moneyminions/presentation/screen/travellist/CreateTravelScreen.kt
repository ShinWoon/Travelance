package com.moneyminions.presentation.screen.travellist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CardFrame
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.screen.travellist.util.displayText
import com.moneyminions.presentation.screen.travellist.util.rememberFirstMostVisibleMonth
import com.moneyminions.presentation.screen.travellist.view.Calendar
import com.moneyminions.presentation.theme.DarkGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkMiddle
import com.moneyminions.presentation.theme.TextGray
import com.moneyminions.presentation.viewmodel.travellist.CreateTravelViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateTravelScreen(
    createTravelViewModel: CreateTravelViewModel = hiltViewModel(),
    navController: NavHostController
){
    val createTravelConstraintSet = ConstraintSet{
        val travelNameField = createRefFor("travelNameField")

        constrain(travelNameField){
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "여행 생성"
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            TextFieldWithTitle(
                title = "이름",
                hint = "여행 이름을 입력하세요",
                value = createTravelViewModel.travelName.value,
                onValueChange = {
                    createTravelViewModel.setTravelName(it)
                }
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            TextFieldWithTitle(
                title = "예산",
                hint = "예산을 입력해주세요",
                value = createTravelViewModel.travelBudget.value,
                onValueChange = {
                    createTravelViewModel.setTravelBudget(it)
                },
                keyboardType = KeyboardType.Number
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Calendar()
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "시작 날짜 : ",
                    style = CustomTextStyle.pretendardSemiBold16
                )
                Spacer(
                    modifier = Modifier.size(4.dp)
                )
                Text(
                    text = createTravelViewModel.startDate.value,
                    style = CustomTextStyle.pretendardSemiBold16
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "종료 날짜 : ",
                    style = CustomTextStyle.pretendardSemiBold16
                )
                Spacer(
                    modifier = Modifier.size(4.dp)
                )
                Text(
                    text = createTravelViewModel.endDate.value,
                    style = CustomTextStyle.pretendardSemiBold16
                )
            }
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            MinionPrimaryButton(
                content = "생성",
                modifier = Modifier.fillMaxWidth()
            ){
                //생성 이벤트
            }
        }

    }

}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CreateTravelScreenPreview(){
    CreateTravelScreen(navController = rememberNavController())
}
