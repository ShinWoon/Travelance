package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutRoleUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
//    operator fun invoke(role: String?){
//        return preferenceRepository.putRole(role)
//    }
}