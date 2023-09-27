package com.moneyminions.domain.repository.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelDetailMyPaymentDto

interface TravelDetailRepository {
    suspend fun getMyPaymentList(email: String): NetworkResult<List<TravelDetailMyPaymentDto>>

    suspend fun getTravelDetailInfo(email: String): NetworkResult<TravelDetailInfoDto>
}
