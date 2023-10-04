package com.moneyminions.data.datasource.remote.traveldetail

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveldetail.request.FinalPaymentRequest
import com.moneyminions.data.model.traveldetail.response.SettleResultResponse
import com.moneyminions.data.model.traveldetail.request.PaymentCompleteRequest
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse

interface TravelDetailDataSource {
    suspend fun getMyPaymentList(roomId: Int): List<TravelDetailMyPaymentResponse>

    suspend fun getTravelDetailInfo(roomId: Int): TravelDetailInfoResponse

    suspend fun updatePaymentInfo(travelPaymentChangeInfoRequest: TravelPaymentChangeInfoRequest): CommonResponse
    suspend fun setSettleState(paymentCompleteRequest: PaymentCompleteRequest): CommonResponse

    suspend fun getSettleResult(roomId: Int): SettleResultResponse

    suspend fun postFinalPayment(finalPaymentRequest: FinalPaymentRequest): CommonResponse
}
