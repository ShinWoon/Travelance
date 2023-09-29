package com.moneyminions.domain.repository.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.home.TravelRoomInfoDto

interface HomeRepository {
    /**
     * 여행방 시작
     */
    suspend fun startTravel(roomId: Int): NetworkResult<CommonResultDto>
    
    /**
     * 특정 여행방 조회
     */
    suspend fun getTravelRoomInfo(roomId: Int): NetworkResult<TravelRoomInfoDto>
}