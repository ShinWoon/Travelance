package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class GetMyPaymentUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
){
    suspend operator fun invoke(roomId: Int): NetworkResult<List<TravelPaymentDto>> {
        return travelDetailRepository.getMyPaymentList(roomId = roomId)
    }
}