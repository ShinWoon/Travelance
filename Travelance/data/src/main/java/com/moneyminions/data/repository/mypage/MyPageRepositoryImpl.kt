package com.moneyminions.data.repository.mypage

import com.moneyminions.data.datasource.remote.mypage.MyPageDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(
    private val myPageDataSource: MyPageDataSource
): MyPageRepository {
    override suspend fun getMemberInfo(): NetworkResult<MemberInfo> {
        return handleApi { myPageDataSource.getMemberInfo().toDomain() }
    }

    override suspend fun updateNickname(nickname: String): NetworkResult<CommonResultDto> {
        return handleApi { myPageDataSource.updateNickname(nickname).toDomain() }
    }

    override suspend fun deleteCard(cardName: String, cardNumber: String): NetworkResult<CommonResultDto> {
        return handleApi { myPageDataSource.deleteCard(cardName, cardNumber).toDomain() }
    }

    override suspend fun deleteAccount(bankName: String, accountNumber: String): NetworkResult<CommonResultDto> {
        return handleApi { myPageDataSource.deleteAccount(bankName, accountNumber).toDomain() }
    }

    override suspend fun addAccountAndCard(memberInfo: MemberInfo): NetworkResult<CommonResultDto> {
        return handleApi { myPageDataSource.addAccountAndCard(memberInfo.toData()).toDomain() }
    }
}