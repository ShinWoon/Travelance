package com.moneyminions.data.datasource.remote.travellist

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse

interface TravelListDataSource {
    /**
     * 여행방 생성
     */
    suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest): CommonResponse

    /**
     * 여행방 리스트 조회
     */
    suspend fun getTravelList(): List<TravelRoomResponse>

    /**
     * 여행방 삭제
     */
    suspend fun deleteTravelRoom(roomId: Int): CommonResponse
    

}