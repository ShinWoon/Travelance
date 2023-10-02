package com.moneyminions.data.datasource.remote.home

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.TravelRoomFriendsResponse
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
    
    /**
     * 현금 사용 등록
     */
    suspend fun requestUseCash(useCashRequest: UseCashRequest): CommonResponse

    /**
     * 여행 친구 목록 조회
     */
    suspend fun getTravelRoomFriends(roomId: Int): List<TravelRoomFriendsResponse>
}