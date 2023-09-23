package com.moneyminions.data.repository.travellist

import android.util.Log
import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject


private const val TAG = "TravelListRepositoryImpl_D210"
class TravelListRepositoryImpl @Inject constructor(
    val travelListDataSource: TravelListDataSource
): TravelListRepository{
    override suspend fun createTravelRoom(travelRoomDto: TravelRoomDto): NetworkResult<String> {
        Log.d(TAG, "createTravelRoom: ${travelRoomDto.toData()}")
        return handleApi{ travelListDataSource.createTravelRoom(travelRoomDto.toData()) }
    }

    override suspend fun getTravelList(): NetworkResult<List<TravelRoomDto>> {
        return handleApi { travelListDataSource.getTravelList().map { it.toDomain() } }
    }
}