package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse

interface TravelDetailDataSource {
    suspend fun getMyPaymentList(): List<TravelDetailMyPaymentResponse>

    suspend fun getTravelDetailInfo(): TravelDetailInfoResponse

    suspend fun updatePaymentInfo(travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): CommonResponse
}
