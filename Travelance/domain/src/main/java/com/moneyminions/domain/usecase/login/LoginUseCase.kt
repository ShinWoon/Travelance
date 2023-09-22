package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.login.LoginResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(socialType: String): NetworkResult<LoginResultDto>{
        return loginRepository.login(socialType)
    }
}