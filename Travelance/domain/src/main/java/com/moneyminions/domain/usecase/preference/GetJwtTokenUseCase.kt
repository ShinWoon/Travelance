package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetJwtTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): JwtTokenDto{
        return preferenceRepository.getJwtToken()
    }
}