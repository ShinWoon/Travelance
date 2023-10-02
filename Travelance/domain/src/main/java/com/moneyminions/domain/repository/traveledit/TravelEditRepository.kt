package com.moneyminions.domain.repository.traveledit

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto

interface TravelEditRepository {
    suspend fun editTravelRoom(roomId: Int, travelRoomInfoDto: TravelRoomInfoDto) : NetworkResult<CommonResultDto>
}