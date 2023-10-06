package com.moneyminions.domain.usecase.preference

import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutTravelingRoomIdUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    operator fun invoke(roomId: Int) {
        return preferenceRepository.putTravelingRoomId(roomId = roomId)
    }
}