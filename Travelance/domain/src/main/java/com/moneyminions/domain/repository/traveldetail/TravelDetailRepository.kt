package com.moneyminions.domain.repository.traveldetail

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.FinalPaymentDto
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto

interface TravelDetailRepository {
    suspend fun getMyPaymentList(roomId: Int): NetworkResult<List<TravelPaymentDto>>

    suspend fun getTravelDetailInfo(roomId: Int): NetworkResult<TravelDetailInfoDto>

    suspend fun updateTravelPaymentInfo(travelPaymentChangeInfoDto: TravelPaymentChangeInfoDto): NetworkResult<CommonResultDto>

    suspend fun setSettleState(paymentCompleteDto: PaymentCompleteDto): NetworkResult<CommonResultDto>
    suspend fun getSettleResult(roomId: Int): NetworkResult<SettleResultDto>

    suspend fun postFinalPayment(finalPaymentDto: FinalPaymentDto): NetworkResult<CommonResultDto>
}
