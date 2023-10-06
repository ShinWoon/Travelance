package com.moneyminions.data.mapper

import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto

fun MemberInfoResponse.toDomain(): MemberInfo{
    return MemberInfo(
        accountList = accountList.map { it.toDomain() },
        cardList = cardList.map { it.toDomain() },
        nickname = nickname,
        password = password
    )
}
