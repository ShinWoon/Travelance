package com.moneyminions.data.datasource.remote.traveledit

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest

interface TravelEditDataSource {
    suspend fun editTravelRoom(roomId: Int, travelRoomEditRequest: TravelRoomEditRequest) : CommonResponse

    /**
     * 초대받은 참가자 -> 여행 참가
     */
    suspend fun requestJoinUser(roomId: Int, createTravelRoomRequest: CreateTravelRoomRequest): CommonResponse
}