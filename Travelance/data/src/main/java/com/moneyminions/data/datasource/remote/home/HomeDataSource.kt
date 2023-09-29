package com.moneyminions.data.datasource.remote.home

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse

interface HomeDataSource {
    /**
     * 여행방 시작
     */
    suspend fun startTravel(roomId: Int): CommonResponse
    
    /**
     * 특정 여행방 조회
     */
    suspend fun getSelectTravelRoom(roomId: Int): TravelRoomInfoResponse
}