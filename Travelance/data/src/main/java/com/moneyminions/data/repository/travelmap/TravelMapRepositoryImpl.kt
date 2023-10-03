package com.moneyminions.data.repository.travelmap

import com.moneyminions.data.datasource.remote.travelmap.TravelMapDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto
import com.moneyminions.domain.repository.travelmap.TravelMapRepository

class TravelMapRepositoryImpl(
    private val travelMapDataSource: TravelMapDataSource
): TravelMapRepository {
    override suspend fun getTravelSpots(roomId: Int): NetworkResult<List<TravelMapSpotDto>> {
        return handleApi { travelMapDataSource.getTravelMapInfo(roomId = roomId).map { it.toDomain() }}
    }

    override suspend fun getTravelSpotDetail(
        roomId: Int,
        travelMapStoreAddressDto: TravelMapStoreAddressDto
    ): NetworkResult<List<TravelMapDetailDto>> {
        return handleApi { travelMapDataSource.getTravelSpotDetail(roomId = roomId, travelMapDetailRequest = travelMapStoreAddressDto.toData()).map { it.toDomain() } }
    }

}