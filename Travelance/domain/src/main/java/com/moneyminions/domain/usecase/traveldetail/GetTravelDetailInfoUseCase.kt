package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class GetTravelDetailInfoUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
) {
    suspend operator fun invoke(roomId: Int): NetworkResult<TravelDetailInfoDto> {
        return travelDetailRepository.getTravelDetailInfo(roomId = roomId)
    }
}