package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.MemberInfo
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class JoinUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(memberInfo: MemberInfo): NetworkResult<JwtTokenDto>{
        return loginRepository.join(memberInfo)
    }
}