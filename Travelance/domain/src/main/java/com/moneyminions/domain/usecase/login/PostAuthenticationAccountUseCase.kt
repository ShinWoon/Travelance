package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.AuthenticationAccountInfoDto
import com.moneyminions.domain.model.login.AuthenticationAccountResultDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class PostAuthenticationAccountUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(accountInfoDto: AuthenticationAccountInfoDto): NetworkResult<AuthenticationAccountResultDto>{
        return loginRepository.postAuthenticationAccount(accountInfoDto)
    }
}