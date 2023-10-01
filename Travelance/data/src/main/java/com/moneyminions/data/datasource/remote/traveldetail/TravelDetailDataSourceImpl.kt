package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveldetail.request.PaymentCompleteRequest
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.service.BusinessService

class TravelDetailDataSourceImpl(
    private val businessService: BusinessService
) : TravelDetailDataSource {
    override suspend fun getMyPaymentList(): List<TravelDetailMyPaymentResponse> {
        return businessService.getMyPaymentList()
    }

    override suspend fun getTravelDetailInfo(): TravelDetailInfoResponse {
        return businessService.getTravelDetailInfo()
    }

    override suspend fun updatePaymentInfo(travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): CommonResponse {
        return businessService.updatePaymentInfo(travelPaymentChangeInfoRequest = travelPaymentChangeInfoRequest)
    }

    override suspend fun setSettleState(paymentCompleteRequest: PaymentCompleteRequest): CommonResponse {
        return businessService.setSettleState(paymentCompleteRequest = paymentCompleteRequest)
    }
}
