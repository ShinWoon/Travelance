package com.moneyminions.data.datasource.remote.mypage

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.model.mypage.request.DeleteAccountRequest
import com.moneyminions.data.model.mypage.request.DeleteCardRequest
import com.moneyminions.data.service.BusinessService

class MyPageDataSourceImpl(
    private val businessService: BusinessService
): MyPageDataSource {
    override suspend fun getMemberInfo(): MemberInfoResponse {
        return businessService.getMemberInfo()
    }

    override suspend fun updateNickname(nickname: String): CommonResponse {
        return businessService.updateNickname(nickname)
    }

    override suspend fun deleteCard(deleteCardRequest: DeleteCardRequest): CommonResponse {
        return businessService.deleteCard(deleteCardRequest)
    }

    override suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): CommonResponse {
        return businessService.deleteAccount(deleteAccountRequest)
    }
}