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
    object CreateTravel: Screen(
        name = "CreateTravel",
        route = "create_travel_screen"
    )
    object MyPage: Screen(
        name = "MyPage",
        route = "my_page_screen"
    )
    object Setting: Screen(
        name = "Setting",
        route = "setting_screen"
    )
    object EditUser: Screen(
        name = "EditUser",
        route = "edit_user_screen"
    )
    object AccountAuthentication: Screen(
        name = "AccountAuthentication",
        route = "account_authenthication_screen"
    )
    object AccountList: Screen(
        name = "AccountList",
        route = "account_list_screen"
    )
    object CardList: Screen(
        name = "CardList",
        route = "card_list_screen"
    )
}