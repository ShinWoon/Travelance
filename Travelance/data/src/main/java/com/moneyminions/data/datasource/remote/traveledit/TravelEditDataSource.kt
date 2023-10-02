package com.moneyminions.data.datasource.remote.traveledit

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest

interface TravelEditDataSource {
    suspend fun editTravelRoom(roomId: Int, travelRoomEditRequest: TravelRoomEditRequest) : CommonResponse
}