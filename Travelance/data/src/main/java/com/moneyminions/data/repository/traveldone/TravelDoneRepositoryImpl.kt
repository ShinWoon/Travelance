package com.moneyminions.data.repository.traveldone

import com.moneyminions.data.datasource.remote.traveldone.TravelDoneDataSource
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import com.moneyminions.domain.repository.traveldone.TravelDoneRepository

class TravelDoneRepositoryImpl(
    private val travelDoneDataSource: TravelDoneDataSource
): TravelDoneRepository {
    override suspend fun getTravelDoneInfo(roomId: Int): NetworkResult<TravelDoneInfoTotalDto> {
        return handleApi { travelDoneDataSource.getTravelDoneInfo(roomId).toDomain() }
    }
}