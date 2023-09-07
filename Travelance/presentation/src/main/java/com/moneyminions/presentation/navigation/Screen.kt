package com.moneyminions.presentation.navigation

sealed class Screen(val name: String, val route: String) {
    object Example: Screen(
        name = "Example",
        route = "example_screen"
    )
    object Home: Screen(
        name = "Home",
        route = "home_screen"
    )
    object TravelList: Screen(
        name = "TravelList",
        route = "travel_list_screen"
    )
}