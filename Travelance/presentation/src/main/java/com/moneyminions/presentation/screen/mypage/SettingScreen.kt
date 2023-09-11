package com.moneyminions.presentation.screen.mypage

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingScreen(
    navController: NavHostController
){

}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview(){
    SettingScreen(navController = rememberNavController())
}