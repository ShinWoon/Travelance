package com.moneyminions.data.datasource.remote.mypage

import com.moneyminions.data.model.login.response.MemberInfoResponse

interface MyPageDataSource {
    suspend fun getMemberInfo(): MemberInfoResponse
}