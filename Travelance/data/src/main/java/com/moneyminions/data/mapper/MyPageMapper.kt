package com.moneyminions.data.mapper

import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.model.mypage.request.DeleteAccountRequest
import com.moneyminions.data.model.mypage.request.DeleteCardRequest
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

fun CardDto.toDeleteData(): DeleteCardRequest{
    return DeleteCardRequest(
        cardCoName = name,
        cardNumber = number
    )
}

fun AccountDto.toDeleteData(): DeleteAccountRequest{
    return DeleteAccountRequest(
        account = accountNumber!!,
        accountName = bankName
    )
}