package com.moneyminions.presentation.viewmodel.mypage

import androidx.lifecycle.ViewModel
import com.moneyminions.domain.model.common.CardDto
import javax.inject.Inject

class MyPageViewModel @Inject constructor(

): ViewModel() {
    val cardList = listOf(
            CardDto(
                name = "카드별칭 1", number = "1234567812345678", idx = 0),
            CardDto(
                name = "카드별칭 2", number = "1234567812345678", idx = 1),
            CardDto(
                name = "카드별칭 3", number = "1234567812345678", idx = 2),
        )

}