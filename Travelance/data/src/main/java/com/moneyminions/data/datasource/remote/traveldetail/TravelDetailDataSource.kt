package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse

interface TravelDetailDataSource {
    suspend fun getMyPaymentList(email: String): List<TravelDetailMyPaymentResponse>

    suspend fun getTravelDetailInfo(email: String): TravelDetailInfoResponse
}
