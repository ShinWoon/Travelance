package com.moneyminions.presentation.screen.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White

val accountList = listOf(
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    ),
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    ),
    AccountDto(
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png",
        name = "신한",
        number = "997838829102"
    )
)
val cardList = listOf(
    CardDto(
        name = "카드 별칭 1",
        number = "123456789012",
        idx = 0,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    ),
    CardDto(
        name = "카드 별칭 2",
        number = "123456789012",
        idx = 1,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    ),
    CardDto(
        name = "카드 별칭 3",
        number = "123456789012",
        idx = 2,
        company = "카드사명",
        logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"
    )
)

@Composable
fun EditUserScreen(
    navController: NavHostController
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "회원 정보 수정"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Spacer(modifier = Modifier.size(16.dp))
            EditName(
                value = "",
                onValueChange = {

                },
                onClick = { //완료 버튼 눌렀을 때

                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "계좌 목록",
                style = CustomTextStyle.pretendardBold16
            )
            Spacer(modifier = Modifier.size(8.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GraphGray)
                .padding(horizontal = 16.dp)
            )
            LazyColumn{
                items(accountList){
                    AccountRowItem(
                        logo = it.logo,
                        name = it.name,
                        number = it.number,
                        type = "delete",
                        onDeleted = {
                            // 삭제 로직
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            PlusButtonWithText(text = "계좌 추가하기") {
                //계좌 추가하기 화면으로 이동
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "카드 목록",
                style = CustomTextStyle.pretendardBold16
            )
            Spacer(modifier = Modifier.size(8.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GraphGray)
                .padding(horizontal = 16.dp)
            )
            LazyColumn{
                items(cardList){
                    CardRowItem(
                        name = it.name,
                        number = it.number,
                        idx = it.idx,
                        type = "delete",
                        onDeleted = {
                            //삭제 로직
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            PlusButtonWithText(text = "카드 추가하기") {
                //카드 추가하기 화면으로 이동
            }
        }
    }
}

@Composable
fun PlusButtonWithText(
    text: String,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_rounded_plus),
            contentDescription = "add button",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onClick()
                }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            style = CustomTextStyle.pretendardMedium12
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditName(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
){
    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PinkLight, // 포커스가 있을 때 테두리 색상
        unfocusedBorderColor = GraphGray, // 포커스가 없을 때 테두리 색상
        cursorColor = Color.Black, // 커서 색상
    )

    Text(
        text = "닉네임",
        style = CustomTextStyle.pretendardBold16
    )
    Spacer(
        modifier = Modifier.size(16.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "닉네임을 입력해주세요",
                    style = CustomTextStyle.pretendardRegular12,
                    color = GraphGray
                )
            },
            singleLine = true,
            colors = outlinedTextFieldColors,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(0.7f) // 80%의 가중치
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Button(
            modifier = Modifier.weight(0.2f),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkDarkest,
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            onClick = onClick
        ) {
            Text(
                text = "완료",
                style = CustomTextStyle.pretendardBold12,
                color = White
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun EditUserScreenPreview(){
    EditUserScreen(navController = rememberNavController())
}