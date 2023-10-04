package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveldetail.request.FinalPaymentRequest
import com.moneyminions.data.model.traveldetail.response.SettleResultResponse
import com.moneyminions.data.model.traveldetail.request.PaymentCompleteRequest
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.service.BusinessService

class TravelDetailDataSourceImpl(
    private val businessService: BusinessService
) : TravelDetailDataSource {
    override suspend fun getMyPaymentList(roomId: Int): List<TravelDetailMyPaymentResponse> {
        return businessService.getMyPaymentList(roomId = roomId)
    }

    override suspend fun getTravelDetailInfo(roomId: Int): TravelDetailInfoResponse {
        return businessService.getTravelDetailInfo(roomId)
    }

    override suspend fun updatePaymentInfo(travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): CommonResponse {
        return businessService.updatePaymentInfo(travelPaymentChangeInfoRequest = travelPaymentChangeInfoRequest)
    }

    override suspend fun setSettleState(paymentCompleteRequest: PaymentCompleteRequest): CommonResponse {
        return businessService.setSettleState(paymentCompleteRequest = paymentCompleteRequest)
    }

    override suspend fun getSettleResult(roomId: Int): SettleResultResponse {
        return businessService.getSettleResult(roomId)
    }

    override suspend fun postFinalPayment(finalPaymentRequest: FinalPaymentRequest): CommonResponse {
        return businessService.postFinalPayment(finalPaymentRequest)
    }
}
