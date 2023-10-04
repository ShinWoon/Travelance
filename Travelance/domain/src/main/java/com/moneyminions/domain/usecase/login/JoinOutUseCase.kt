package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class JoinOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): NetworkResult<CommonResultDto>{
        return loginRepository.joinOut()
    }
}