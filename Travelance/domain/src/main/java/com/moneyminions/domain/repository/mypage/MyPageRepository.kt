package com.moneyminions.domain.repository.mypage

import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult

interface MyPageRepository {
    suspend fun getMemberInfo(): NetworkResult<MemberInfo>
}