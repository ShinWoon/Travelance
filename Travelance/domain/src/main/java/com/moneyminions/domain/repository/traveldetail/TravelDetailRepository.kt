package com.moneyminions.domain.repository.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto

interface TravelDetailRepository {
    suspend fun getMyPaymentList(): NetworkResult<List<TravelPaymentDto>>

    suspend fun getTravelDetailInfo(): NetworkResult<TravelDetailInfoDto>
}
