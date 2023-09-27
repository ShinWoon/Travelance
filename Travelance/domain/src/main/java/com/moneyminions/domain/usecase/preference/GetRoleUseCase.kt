package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetRoleUseCase@Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
//    operator fun invoke(): String{
//        return preferenceRepository.getRole()
//    }
}