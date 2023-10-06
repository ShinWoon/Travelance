package com.moneyminions.domain.usecase.traveldone

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import com.moneyminions.domain.repository.traveldone.TravelDoneRepository
import javax.inject.Inject

class GetTravelDoneInfoUseCase @Inject constructor(
    private val travelDoneRepository: TravelDoneRepository,
) {
    suspend operator fun invoke(roomId: Int): NetworkResult<TravelDoneInfoTotalDto> {
        return travelDoneRepository.getTravelDoneInfo(roomId = roomId)
    }
}
