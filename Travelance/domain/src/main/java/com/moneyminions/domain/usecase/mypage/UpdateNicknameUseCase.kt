package com.moneyminions.domain.usecase.mypage

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class UpdateNicknameUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(nickname: String): NetworkResult<CommonResultDto>{
        return myPageRepository.updateNickname(nickname)
    }
}