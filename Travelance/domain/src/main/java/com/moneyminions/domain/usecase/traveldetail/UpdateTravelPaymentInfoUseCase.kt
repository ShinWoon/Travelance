package com.moneyminions.domain.usecase.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class UpdateTravelPaymentInfoUseCase @Inject constructor(
    private val travelDetailRepository: TravelDetailRepository
) {
    suspend operator fun invoke(travelPaymentChangeInfo: TravelPaymentChangeInfoDto): NetworkResult<CommonResultDto> {
        return travelDetailRepository.updateTravelPaymentInfo(travelPaymentChangeInfo)
    }
}