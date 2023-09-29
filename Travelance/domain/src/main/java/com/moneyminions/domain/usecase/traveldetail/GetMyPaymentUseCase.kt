package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailMyPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class GetMyPaymentUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
){
    suspend operator fun invoke(email: String): NetworkResult<List<TravelDetailMyPaymentDto>> {
        return travelDetailRepository.getMyPaymentList(email)
    }
}