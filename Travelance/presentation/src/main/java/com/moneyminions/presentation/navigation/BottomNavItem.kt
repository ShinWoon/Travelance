package com.moneyminions.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

private const val TAG = "BottomNavItem_D210"
sealed class BottomNavItem(val name: String, val route: String, val icon: ImageVector) {
    object Home: BottomNavItem(
        name = "Home",
        route = Screen.Home.route,
        icon = Icons.Rounded.Home
    )

    object TravelList: BottomNavItem(
        name = "TravelList",
        route = Screen.TravelList.route,
        icon = Icons.Rounded.List
    )

    object MyPage: BottomNavItem(
        name = "MyPage",
        route = Screen.MyPage.route,
        icon = Icons.Rounded.Person
    )
}