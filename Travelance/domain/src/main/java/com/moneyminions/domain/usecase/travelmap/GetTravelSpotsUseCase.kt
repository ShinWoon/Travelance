package com.moneyminions.domain.usecase.travelmap

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.repository.travelmap.TravelMapRepository
import javax.inject.Inject

class GetTravelSpotsUseCase @Inject constructor(
    private val travelMapRepository: TravelMapRepository
) {
    suspend operator fun invoke(roomId: Int): NetworkResult<List<TravelMapSpotDto>> {
        return travelMapRepository.getTravelSpots(roomId)
    }
}