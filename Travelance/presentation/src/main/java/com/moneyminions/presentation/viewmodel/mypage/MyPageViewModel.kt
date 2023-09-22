package com.moneyminions.presentation.viewmodel.mypage

import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.common.CardDto
import javax.inject.Inject

class MyPageViewModel @Inject constructor(

): ViewModel() {
    val cardList = listOf(
            CardDto(
                name = "카드별칭 1", number = "1234567812345678", idx = 0, company = "카드사명",
                logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"),
            CardDto(
                name = "카드별칭 2", number = "1234567812345678", idx = 1, company = "카드사명",
                logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"),
            CardDto(
                name = "카드별칭 3", number = "1234567812345678", idx = 2, company = "카드사명",
                logo = "https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png"),
        )

}