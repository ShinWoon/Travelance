package com.moneyminions.data.repository.traveldetail

import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.FinalPaymentDto
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class TravelDetailRepositoryImpl @Inject constructor(
    private val travelDetailDataSource: TravelDetailDataSource
) : TravelDetailRepository {
    override suspend fun getMyPaymentList(roomId: Int): NetworkResult<List<TravelPaymentDto>> {
        return handleApi { travelDetailDataSource.getMyPaymentList(roomId = roomId).map { it.toDomain() } }
    }

    override suspend fun getTravelDetailInfo(roomId: Int): NetworkResult<TravelDetailInfoDto> {
        return handleApi { travelDetailDataSource.getTravelDetailInfo(roomId = roomId).toDomain() }
    }

    override suspend fun updateTravelPaymentInfo(travelPaymentChangeInfoDto: TravelPaymentChangeInfoDto): NetworkResult<CommonResultDto> {
        return handleApi { travelDetailDataSource.updatePaymentInfo(travelPaymentChangeInfoDto.toData()).toDomain() }
    }

    override suspend fun setSettleState(paymentCompleteDto: PaymentCompleteDto): NetworkResult<CommonResultDto> {
        return handleApi { travelDetailDataSource.setSettleState(paymentCompleteDto.toData()).toDomain() }
    }

    override suspend fun getSettleResult(roomId: Int): NetworkResult<SettleResultDto> {
        return handleApi { travelDetailDataSource.getSettleResult(roomId).toDomain() }
    }

    override suspend fun postFinalPayment(finalPaymentDto: FinalPaymentDto): NetworkResult<CommonResultDto> {
        return handleApi { travelDetailDataSource.postFinalPayment(finalPaymentDto.toData()).toDomain() }
    }
}
