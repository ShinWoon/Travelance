package com.moneyminions.domain.usecase.mypage

import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMemberInfoUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(): NetworkResult<MemberInfo>{
        return myPageRepository.getMemberInfo()
    }
}