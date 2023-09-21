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
import com.moneyminions.presentation.common.AccountRowItem
import com.moneyminions.presentation.common.CardRowItem
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.screen.mypage.view.AccountListComponent
import com.moneyminions.presentation.screen.mypage.view.CardListComponent
import com.moneyminions.presentation.screen.mypage.view.EditName
import com.moneyminions.presentation.screen.mypage.view.accountList
import com.moneyminions.presentation.screen.mypage.view.cardList
import com.moneyminions.presentation.screen.travellist.util.clickable
import com.moneyminions.presentation.theme.GraphGray
import com.moneyminions.presentation.theme.PinkDarkest
import com.moneyminions.presentation.theme.PinkLight
import com.moneyminions.presentation.theme.White

@Composable
fun EditUserScreen(
    navController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopBar(
            navController = navController,
            title = "회원 정보 수정",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            EditName(
                value = "",
                onValueChange = {
                },
                onClick = { // 완료 버튼 눌렀을 때
                },
            )
            Spacer(modifier = Modifier.size(16.dp))
            AccountListComponent()
            Spacer(modifier = Modifier.size(16.dp))
            CardListComponent()
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun EditUserScreenPreview() {
    EditUserScreen(navController = rememberNavController())
}
