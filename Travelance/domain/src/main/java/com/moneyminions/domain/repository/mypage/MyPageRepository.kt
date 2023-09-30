package com.moneyminions.domain.repository.mypage

import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto

interface MyPageRepository {
    suspend fun getMemberInfo(): NetworkResult<MemberInfo>
    suspend fun updateNickname(nickname: String): NetworkResult<CommonResultDto>
    suspend fun deleteCard(deleteCard: CardDto): NetworkResult<CommonResultDto>
    suspend fun deleteAccount(deleteAccount: AccountDto): NetworkResult<CommonResultDto>
}