package com.moneyminions.presentation.navigation

sealed class Screen(val name: String, val route: String) {
    object Example : Screen(
        name = "Example",
        route = "example_screen",
    )
    object Main : Screen(
        name = "Main",
        route = "main_screen",
    )
    object Home : Screen(
        name = "Home",
        route = "home_screen",
    )
    object TravelList : Screen(
        name = "TravelList",
        route = "travel_list_screen",
    )
    object CreateTravel : Screen(
        name = "CreateTravel",
        route = "create_travel_screen",
    )
    object MyPage : Screen(
        name = "MyPage",
        route = "my_page_screen",
    )
    object Setting : Screen(
        name = "Setting",
        route = "setting_screen",
    )
    object Announcement : Screen(
        name = "Announcement",
        route = "Announcement_screen",
    )
    object EditUser : Screen(
        name = "EditUser",
        route = "edit_user_screen",
    )
    object AccountAuthentication : Screen(
        name = "AccountAuthentication",
        route = "account_authenthication_screen",
    )
    object AccountList : Screen(
        name = "AccountList",
        route = "account_list_screen",
    )
    object CardList : Screen(
        name = "CardList",
        route = "card_list_screen",
    )
    object TravelMap : Screen(
        name = "TravelMap",
        route = "travel_map_screen",
    )
    object GameList : Screen(
        name = "GameList",
        route = "game_List_screen",
    )
    object CardGame : Screen(
        name = "CardGame",
        route = "card_game_screen",
    )
    object BottleGame : Screen(
        name = "BottleGame",
        route = "bottle_game_screen",
    )
    object TeamBuildingGame : Screen(
        name = "TeamBuildingGame",
        route = "team_building_game_screen",
    )
    object WordGame : Screen(
        name = "WordGame",
        route = "word_game_screen",
    )
    object Login : Screen(
        name = "Login",
        route = "login_screen",
    )
    object SubHome: Screen(
        name = "SubHome",
        route = "sub_home_screen"
    )
    object TravelDetail : Screen(
        name = "TravelDetail",
        route = "travel_detail",
    )
    object NicknamePassword: Screen(
        name = "NicknamePassword",
        route = "nickname_password"
    )
}

