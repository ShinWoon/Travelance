package com.moneyminions.domain.model

import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto

data class MemberInfo(
    var accountList: List<AccountDto>,
    var cardList: List<CardDto>,
    var nickname: String,
    var password: String
)
