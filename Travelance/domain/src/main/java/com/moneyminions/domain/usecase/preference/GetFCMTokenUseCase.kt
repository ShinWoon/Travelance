package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetFCMTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): String{
        return preferenceRepository.getFCMToken()
    }
}