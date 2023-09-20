package com.moneyminions.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.AuthenticationDialog
import com.moneyminions.presentation.common.CustomTextStyle
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.screen.login.view.AuthenticAccountListComponent
import com.moneyminions.presentation.screen.mypage.AccountDto
import com.moneyminions.presentation.screen.mypage.view.AccountColumnItem
import com.moneyminions.presentation.viewmodel.login.AccountAuthenticationViewModel

private const val TAG = "AccountAuthenticationSc D210"
@Composable
fun AccountAuthenticationScreen(
    navController: NavHostController,
    accountAuthenticationViewModel: AccountAuthenticationViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "계좌 인증"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "이름",
                    hint = "이름을 입력하세요",
                    value = "",
                    onValueChange = {

                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                AuthenticAccountListComponent()
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "게좌번호",
                    hint = "계좌 번호를 입력하세요",
                    value = "",
                    onValueChange = {

                    },
                    keyboardType = KeyboardType.Number
                )

            }
            MinionPrimaryButton(
                content = "인증",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
//                navController.navigate(Screen.AccountList.route)
                showDialog = true
            }
        }
    }

    AuthenticationDialog(
        navController = navController,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        type = "account",
        value = accountAuthenticationViewModel.authenticationCode.value,
        onValueChange = {
            accountAuthenticationViewModel.setAuthenticationCode(it)
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AccountAuthenticationScreenPreview(){
    AccountAuthenticationScreen(navController = rememberNavController())
}