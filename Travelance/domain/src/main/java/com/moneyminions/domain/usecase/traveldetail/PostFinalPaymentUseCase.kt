package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.FinalPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class PostFinalPaymentUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
) {
    suspend operator fun invoke(finalPaymentDto: FinalPaymentDto): NetworkResult<CommonResultDto>{
        return travelDetailRepository.postFinalPayment(finalPaymentDto)
    }
}