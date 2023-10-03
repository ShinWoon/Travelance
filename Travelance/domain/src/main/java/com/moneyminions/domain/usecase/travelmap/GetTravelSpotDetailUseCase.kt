package com.moneyminions.domain.usecase.travelmap

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto
import com.moneyminions.domain.repository.travelmap.TravelMapRepository
import javax.inject.Inject

class GetTravelSpotDetailUseCase @Inject constructor(
    private val travelMapRepository: TravelMapRepository
) {
    suspend operator fun invoke(roomId: Int, travelMapStoreAddressDto: TravelMapStoreAddressDto): NetworkResult<List<TravelMapDetailDto>> {
        return travelMapRepository.getTravelSpotDetail(roomId = roomId, travelMapStoreAddressDto = travelMapStoreAddressDto)
    }
}