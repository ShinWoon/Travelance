package com.moneyminions.data.repository.traveldetail

import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class TravelDetailRepositoryImpl @Inject constructor(
    private val travelDetailDataSource: TravelDetailDataSource
) : TravelDetailRepository {
    override suspend fun getMyPaymentList(): NetworkResult<List<TravelPaymentDto>> {
        return handleApi { travelDetailDataSource.getMyPaymentList().map { it.toDomain() } }
    }

    override suspend fun getTravelDetailInfo(): NetworkResult<TravelDetailInfoDto> {
        return handleApi { travelDetailDataSource.getTravelDetailInfo().toDomain() }
    }

    override suspend fun updateTravelPaymentInfo(travelPaymentChangeInfoDto: TravelPaymentChangeInfoDto): NetworkResult<CommonResultDto> {
        return handleApi { travelDetailDataSource.updatePaymentInfo(travelPaymentChangeInfoDto.toData()).toDomain() }
    }

}
