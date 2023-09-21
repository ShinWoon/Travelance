package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.model.example.ExampleDto
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.PreferenceRepository
import com.moneyminions.domain.repository.example.ExampleRepository
import javax.inject.Inject

class PutJwtTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(jwtTokenDto: JwtTokenDto){
        return preferenceRepository.putJwtToken(jwtTokenDto)
    }
}