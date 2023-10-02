package com.moneyminions.data.datasource.remote.traveledit

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest
import com.moneyminions.data.service.BusinessService

class TravelEditDataSourceImpl(
    private val businessService: BusinessService
) : TravelEditDataSource {
    override suspend fun editTravelRoom(
        roomId: Int,
        travelRoomEditRequest: TravelRoomEditRequest
    ): CommonResponse {
        return businessService.editTravelRoom(roomId = roomId, travelRoomEditRequest = travelRoomEditRequest)
    }
}