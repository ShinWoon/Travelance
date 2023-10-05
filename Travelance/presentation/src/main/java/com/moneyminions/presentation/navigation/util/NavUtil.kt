package com.moneyminions.presentation.navigation.util

import com.moneyminions.presentation.navigation.NavRouteName
import com.moneyminions.presentation.navigation.NavTitle

fun getScreenTitle(route: String): String = when(if (route.contains("/")) route.split("/")[0] else route) {
    NavRouteName.CREATETRAVEL -> NavTitle.CREATETRAVEL
    NavRouteName.SETTING -> NavTitle.SETTING
    NavRouteName.EDITUSER -> NavTitle.EDITUSER
    NavRouteName.ANNOUNCEMENT -> NavTitle.ANNOUNCEMENT
    NavRouteName.GAMELIST -> NavTitle.GAMELIST
    NavRouteName.TRAVELMAP -> NavTitle.TRAVELMAP
    NavRouteName.CARDGAME -> NavTitle.CARDGAME
    NavRouteName.BOTTLEGAME -> NavTitle.BOTTLEGAME
    NavRouteName.TEAMBUILDINGGAME -> NavTitle.TEAMBUILDINGGAME
    NavRouteName.WORDGAME -> NavTitle.WORDGAME
    NavRouteName.ACCOUNTAUTHENTICATION -> NavTitle.ACCOUNTAUTHENTICATION
    NavRouteName.ACCOUNTLIST -> NavTitle.ACCOUNTLIST
    NavRouteName.CARDLIST -> NavTitle.CARDLIST
    NavRouteName.NICKNAMEPASSWORD -> NavTitle.NICKNAMEPASSWORD
    NavRouteName.SUBHOME -> NavTitle.SUBHOME
    NavRouteName.EDITTRAVEL -> NavTitle.EDITTRAVEL
    NavRouteName.WAITHOME -> NavTitle.WAITHOME
    NavRouteName.SETTLERESULT -> NavTitle.SETTLERESULT
    else -> ""
}