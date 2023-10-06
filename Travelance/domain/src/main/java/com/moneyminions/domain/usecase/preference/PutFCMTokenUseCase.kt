package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutFCMTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(fcmToken: String){
        preferenceRepository.putFCMToken(fcmToken)
    }
}