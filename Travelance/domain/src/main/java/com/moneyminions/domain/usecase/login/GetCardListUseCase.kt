package com.moneyminions.domain.usecase.login

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.repository.login.LoginRepository
import javax.inject.Inject

class GetCardListUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): NetworkResult<List<CardDto>>{
        return loginRepository.getCardList()
    }
}