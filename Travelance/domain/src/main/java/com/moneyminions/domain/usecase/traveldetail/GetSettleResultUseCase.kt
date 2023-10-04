package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class GetSettleResultUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
) {
    suspend operator fun invoke(roomId: Int): NetworkResult<SettleResultDto>{
        return travelDetailRepository.getSettleResult(roomId)
    }
}