package com.moneyminions.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val name: String, val route: String, val icon: ImageVector) {
    object Home: BottomNavItem(
        name = "Home",
        route = Screen.Home.route,
        icon = Icons.Rounded.Home
    )
}