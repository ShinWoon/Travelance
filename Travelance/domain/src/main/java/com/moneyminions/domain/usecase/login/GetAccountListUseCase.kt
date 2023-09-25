package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.AccountDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class GetAccountListUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): NetworkResult<List<AccountDto>>{
        return loginRepository.getAccountList()
    }
}