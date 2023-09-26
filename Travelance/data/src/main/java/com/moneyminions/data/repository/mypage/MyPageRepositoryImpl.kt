package com.moneyminions.data.repository.mypage

import com.moneyminions.data.datasource.remote.mypage.MyPageDataSource
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(
    private val myPageDataSource: MyPageDataSource
): MyPageRepository {
    override suspend fun getMemberInfo(): NetworkResult<MemberInfo> {
        return handleApi { myPageDataSource.getMemberInfo().toDomain() }
    }
}