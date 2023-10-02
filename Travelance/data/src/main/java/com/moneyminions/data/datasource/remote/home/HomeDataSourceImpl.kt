package com.moneyminions.data.datasource.remote.home

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.TravelRoomFriendsResponse
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse
import com.moneyminions.data.service.BusinessService

class HomeDataSourceImpl (
    private val businessService: BusinessService
): HomeDataSource {
    
    /**
     * 여행방 시작
     */
    override suspend fun startTravel(roomId: Int): CommonResponse {
        return businessService.startTravel(roomId = roomId)
    }
    
    /**
     * 특정 여행방 조회
     */
    override suspend fun getSelectTravelRoom(roomId: Int) : TravelRoomInfoResponse{
        return businessService.getTravelRoomInfo(roomId = roomId)
    }
    
    override suspend fun requestUseCash(useCashRequest: UseCashRequest): CommonResponse {
        return businessService.requestUseCash(useCashRequest = useCashRequest)
    }

    override suspend fun getTravelRoomFriends(roomId: Int): List<TravelRoomFriendsResponse> {
        return businessService.getTravelRoomFriends(roomId = roomId)
    }
}