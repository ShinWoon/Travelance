package com.moneyminions.data.repository.travellist

import android.util.Log
import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject


private const val TAG = "TravelListRepositoryImpl_D210"
class TravelListRepositoryImpl @Inject constructor(
    val travelListDataSource: TravelListDataSource
): TravelListRepository{

    /**
     * 여행방 생성
     */
    override suspend fun createTravelRoom(createTravelRoomDto: CreateTravelRoomDto): NetworkResult<CommonResultDto> {
        Log.d(TAG, "createTravelRoom: ${createTravelRoomDto.toData()}")
        return handleApi{ travelListDataSource.createTravelRoom(createTravelRoomDto.toData()).toDomain() }
    }

    /**
     * 여행방 리스트 조회
     */
    override suspend fun getTravelList(): NetworkResult<List<TravelRoomDto>> {
        return handleApi { travelListDataSource.getTravelList().map { it.toDomain() } }
    }

    /**
     * 여행방 삭제
     */
    override suspend fun deleteTravelRoom(roomId: Int): NetworkResult<CommonResultDto> {
        return handleApi { travelListDataSource.deleteTravelRoom(roomId).toDomain() }
    }
    
    

    


}