package com.moneyminions.data.datasource.remote.mypage

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.MemberInfoResponse

interface MyPageDataSource {
    suspend fun getMemberInfo(): MemberInfoResponse
    suspend fun updateNickname(nickname: String): CommonResponse
    suspend fun deleteCard(cardName: String, cardNumber: String): CommonResponse
    suspend fun deleteAccount(bankName: String, accountNumber: String): CommonResponse
    suspend fun addAccountAndCard(memberInfoRequest: MemberInfoRequest): CommonResponse
}