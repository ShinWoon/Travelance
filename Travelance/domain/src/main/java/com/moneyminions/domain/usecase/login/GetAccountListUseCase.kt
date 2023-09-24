package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class GetAccountListUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(){
        loginRepository.getAccountList()
    }
}