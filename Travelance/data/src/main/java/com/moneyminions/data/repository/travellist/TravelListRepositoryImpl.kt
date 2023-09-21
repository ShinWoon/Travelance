package com.moneyminions.data.repository.travellist

import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject

class TravelListRepositoryImpl @Inject constructor(
    val travelListDataSource: TravelListDataSource
): TravelListRepository{
    override suspend fun createTravelRoom(travelRoomDto: TravelRoomDto) {
        travelListDataSource.createTravelRoom(travelRoomDto.toData())
    }

    override suspend fun getTravelList(): NetworkResult<List<TravelRoomDto>> {
        return handleApi { travelListDataSource.getTravelList().map { it.toDomain() } }
    }

}