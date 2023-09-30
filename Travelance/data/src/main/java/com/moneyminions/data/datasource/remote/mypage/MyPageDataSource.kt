package com.moneyminions.data.datasource.remote.mypage

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.model.mypage.request.DeleteAccountRequest
import com.moneyminions.data.model.mypage.request.DeleteCardRequest

interface MyPageDataSource {
    suspend fun getMemberInfo(): MemberInfoResponse
    suspend fun updateNickname(nickname: String): CommonResponse
    suspend fun deleteCard(deleteCardRequest: DeleteCardRequest): CommonResponse
    suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): CommonResponse
}