package com.moneyminions.data.repository.traveldetail

import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSource
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelDetailMyPaymentDto
import com.moneyminions.domain.repository.traveldetail.TravelDetailRepository
import javax.inject.Inject

class TravelDetailRepositoryImpl @Inject constructor(
    private val travelDetailDataSource: TravelDetailDataSource
) : TravelDetailRepository {
    override suspend fun getMyPaymentList(email: String): NetworkResult<List<TravelDetailMyPaymentDto>> {
        return handleApi { travelDetailDataSource.getMyPaymentList(email).map { it.toDomain() } }
    }

    override suspend fun getTravelDetailInfo(email: String): NetworkResult<TravelDetailInfoDto> {
        return handleApi { travelDetailDataSource.getTravelDetailInfo(email).toDomain() }
    }
}
