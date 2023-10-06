package com.moneyminions.presentation.navigation

import com.moneyminions.presentation.R

private const val TAG = "싸피"
sealed class Screen(val name: String, val route: String, val title: String) {
    object Example : Screen(
        name = "Example",
        route = NavRouteName.EXAMPLE,
        title = NavTitle.EXAMPLE
    )

    object Main : Screen(
        name = "Main",
        route = NavRouteName.MAIN,
        title = NavTitle.MAIN
    )

    object Home : Screen(
        name = "Home",
        route = NavRouteName.HOME,
        title = NavTitle.HOME
    )

    object TravelList : Screen(
        name = "TravelList",
        route = NavRouteName.TRAVELLIST,
        title = NavTitle.TRAVELLIST
    )

    object CreateTravel : Screen(
        name = "CreateTravel",
        route = NavRouteName.CREATETRAVEL,
        title = NavTitle.CREATETRAVEL
    )

    object MyPage : Screen(
        name = "MyPage",
        route = NavRouteName.MYPAGE,
        title = NavTitle.MYPAGE
    )

    object Setting : Screen(
        name = "Setting",
        route = NavRouteName.SETTING,
        title = NavTitle.SETTING
    )

    object Announcement : Screen(
        name = "Announcement",
        route = NavRouteName.ANNOUNCEMENT,
        title = NavTitle.ANNOUNCEMENT
    )

    object EditUser : Screen(
        name = "EditUser",
        route = NavRouteName.EDITUSER,
        title = NavTitle.EDITUSER
    )

    object AccountAuthentication : Screen(
        name = "AccountAuthentication",
        route = NavRouteName.ACCOUNTAUTHENTICATION,
        title = NavTitle.ACCOUNTAUTHENTICATION
    )

    object AccountList : Screen(
        name = "AccountList",
        route = NavRouteName.ACCOUNTLIST,
        title = NavTitle.ACCOUNTLIST
    )

    object CardList : Screen(
        name = "CardList",
        route = NavRouteName.CARDLIST,
        title = NavTitle.CARDLIST
    )

    object TravelMap : Screen(
        name = "TravelMap",
        route = NavRouteName.TRAVELMAP,
        title = NavTitle.TRAVELMAP
    )

    object GameList : Screen(
        name = "GameList",
        route = NavRouteName.GAMELIST,
        title = NavTitle.GAMELIST
    )

    object CardGame : Screen(
        name = "CardGame",
        route = NavRouteName.CARDGAME,
        title = NavTitle.CARDGAME
    )

    object BottleGame : Screen(
        name = "BottleGame",
        route = NavRouteName.BOTTLEGAME,
        title = NavTitle.BOTTLEGAME
    )

    object TeamBuildingGame : Screen(
        name = "TeamBuildingGame",
        route = NavRouteName.TEAMBUILDINGGAME,
        title = NavTitle.TEAMBUILDINGGAME
    )

    object WordGame : Screen(
        name = "WordGame",
        route = NavRouteName.WORDGAME,
        title = NavTitle.WORDGAME
    )

    object Login : Screen(
        name = "Login",
        route = NavRouteName.LOGIN,
        title = NavTitle.LOGIN
    )

    object SubHome : Screen(
        name = "SubHome",
        route = NavRouteName.SUBHOME,
        title = NavTitle.SUBHOME
    )

    object TravelDetail : Screen(
        name = "TravelDetail",
        route = NavRouteName.TRAVELDETAIL,
        title = NavTitle.TRAVELDETAIL
    )

    object NicknamePassword : Screen(
        name = "NicknamePassword",
        route = NavRouteName.NICKNAMEPASSWORD,
        title = NavTitle.NICKNAMEPASSWORD
    )

    object TravelDone : Screen(
        name = "TravelDone",
        route = NavRouteName.TRAVELDONE,
        title = NavTitle.TRAVELDONE
    )

    object TravelEdit: Screen(
        name = "TravelEdit",
        route = NavRouteName.EDITTRAVEL,
        title = NavTitle.EDITTRAVEL
    )

    object WebView: Screen(
        name = "WebView",
        route = NavRouteName.WEBVIEW,
        title = NavTitle.WEBVIEW
    )
    
    object WaitHome: Screen(
        name = "WaitHome",
        route = NavRouteName.WAITHOME,
        title = NavTitle.WAITHOME
    )

    object SettleResult: Screen(
        name = "SettleResult",
        route = NavRouteName.SETTLERESULT,
        title = NavTitle.SETTLERESULT
    )

    companion object {
        fun checkToolBar(route: String): Boolean {
            return when (if (route.contains("/")) route.split("/")[0] else route) {
                Main.route, Home.route, TravelList.route, MyPage.route, Login.route, TravelDetail.route, TravelDone.route ->  true
                else -> false
            }
        }

        fun getToolBarIcon(route: String): Int {
            return when (route) {
                TravelMap.route -> R.drawable.ic_map_point
                GameList.route -> R.drawable.ic_game
                Announcement.route -> R.drawable.ic_speaker
                else -> 0
            }
        }
    }
}

object NavRouteName {
    const val EXAMPLE = "example_screen"
    const val MAIN = "main_screen"
    const val LOGIN = "login_screen"
    const val HOME = "home_screen"
    const val TRAVELLIST = "travel_list_screen"
    const val MYPAGE = "my_page_screen"
    const val CREATETRAVEL = "create_travel_screen"
    const val SETTING = "setting_screen"
    const val EDITUSER = "edit_user_screen"
    const val TRAVELDETAIL = "travel_detail"
    const val ANNOUNCEMENT = "announcement_screen"
    const val GAMELIST = "game_List_screen"
    const val TRAVELMAP = "travel_map_screen"
    const val CARDGAME = "card_game_screen"
    const val BOTTLEGAME = "bottle_game_screen"
    const val TEAMBUILDINGGAME = "team_building_game_screen"
    const val WORDGAME = "word_game_screen"
    const val ACCOUNTAUTHENTICATION = "account_authenthication_screen"
    const val ACCOUNTLIST = "account_list_screen"
    const val CARDLIST = "card_list_screen"
    const val NICKNAMEPASSWORD = "nickname_password"
    const val SUBHOME = "sub_home_screen"
    const val WAITHOME = "wait_home_screen"
    const val TRAVELDONE = "travel_done"
    const val EDITTRAVEL = "edit_travel_screen"
    const val WEBVIEW = "webview_screen"
    const val SETTLERESULT = "settle_result"
}

object NavTitle {
    const val EXAMPLE = "예시"
    const val MAIN = "메인"
    const val LOGIN = "로그인"
    const val HOME = "홈"
    const val TRAVELLIST = "여행 목록"
    const val MYPAGE = "마이 페이지"
    const val CREATETRAVEL = "여행 생성"
    const val SETTING = "설정"
    const val EDITUSER = "회원 정보 수정"
    const val TRAVELDETAIL = "여행 상세"
    const val ANNOUNCEMENT = "필독"
    const val GAMELIST = "게임"
    const val TRAVELMAP = "발자취"
    const val CARDGAME = "카드 뽑기"
    const val BOTTLEGAME = "지목하기"
    const val TEAMBUILDINGGAME = "팀 정하기"
    const val WORDGAME = "초성게임"
    const val ACCOUNTAUTHENTICATION = "1원 인증"
    const val ACCOUNTLIST = "계좌 목록"
    const val CARDLIST = "카드 목록"
    const val NICKNAMEPASSWORD = "프로필 설정"
    const val SUBHOME = "사전정산"
    const val WAITHOME = "정산 요청 상태"
    const val TRAVELDONE = "여행 완료"
    const val EDITTRAVEL = "여행 수정"
    const val WEBVIEW = "웹뷰"
    const val SETTLERESULT = "정산결과"
}