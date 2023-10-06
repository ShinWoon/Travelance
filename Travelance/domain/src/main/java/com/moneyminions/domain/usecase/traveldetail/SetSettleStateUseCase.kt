package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class SetSettleStateUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
) {
    suspend operator fun invoke(paymentCompleteDto: PaymentCompleteDto): NetworkResult<CommonResultDto> {
        return travelDetailRepository.setSettleState(paymentCompleteDto = paymentCompleteDto)
    }
}