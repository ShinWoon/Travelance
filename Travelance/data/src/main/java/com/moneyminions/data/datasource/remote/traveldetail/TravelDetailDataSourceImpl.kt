package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.service.BusinessService

class TravelDetailDataSourceImpl(
    private val businessService: BusinessService
) : TravelDetailDataSource {
    override suspend fun getMyPaymentList(email: String): List<TravelDetailMyPaymentResponse> {
        return businessService.getMyPaymentList(email)
    }

    override suspend fun getTravelDetailInfo(email: String): TravelDetailInfoResponse {
        return businessService.getTravelDetailInfo(email)
    }
}
