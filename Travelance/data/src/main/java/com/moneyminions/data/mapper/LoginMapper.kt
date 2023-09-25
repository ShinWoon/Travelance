package com.moneyminions.data.mapper

import com.moneyminions.data.model.login.request.AccountRequest
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.CardRequest
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.CardResponse
import com.moneyminions.data.model.login.response.JoinResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto

fun LoginResponse.toDomain(): LoginResultDto {
    return LoginResultDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        role = role
    )
}

fun AuthenticationAccountInfoDto.toData(): AuthenticationAccountRequest{
    return AuthenticationAccountRequest(
        name = name,
        bankName = bankName,
        account = accountNumber,
        verifyCode = verifyCode?: ""
    )
}
fun AuthenticationAccountResponse.toDomain(): AuthenticationAccountResultDto{
    return AuthenticationAccountResultDto(
        verifyCode = verifyCode
    )
}

fun AccountResponse.toDomain(): AccountDto {
    return AccountDto(
        idx = idx,
        bankName = bankName,
        accountNumber = account
    )
}

fun AccountDto.toData(): AccountRequest{
    return AccountRequest(
        account = accountNumber!!,
        bankName = bankName,
        idx = idx
    )
}

fun CardResponse.toDomain(): CardDto{
    return CardDto(
        name = cardCoName,
        number = cardNumber,
        idx = idx
    )
}

fun CardDto.toData(): CardRequest{
    return CardRequest(
        cardCoName = name,
        cardNumber = number,
        idx = idx
    )
}

fun JoinResponse.toDomain(): JwtTokenDto{
    return JwtTokenDto(
        accessToken = accessToken,
        role = role
    )
}

fun MemberInfo.toData(): MemberInfoRequest{
    return MemberInfoRequest(
        accountList = accountList.map { it.toData() },
        cardList = cardList.map { it.toData() },
        nickname = nickname,
        password = password
    )
}