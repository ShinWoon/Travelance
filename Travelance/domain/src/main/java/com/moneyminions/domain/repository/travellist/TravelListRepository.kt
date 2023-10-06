package com.moneyminions.domain.repository.travellist

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.model.travellist.TravelRoomDto


interface TravelListRepository {

    /**
     * 여행방 생성
     */
    suspend fun createTravelRoom(createTravelRoomDto: CreateTravelRoomDto): NetworkResult<CommonResultDto>
    
    /**
     * 여행방 리스트 조회
     */
    suspend fun getTravelList(): NetworkResult<List<TravelRoomDto>>

    /**
     * 여행방 삭제
     */
    suspend fun deleteTravelRoom(roomId: Int): NetworkResult<CommonResultDto>
    
}