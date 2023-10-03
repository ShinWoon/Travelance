package com.moneyminions.domain.repository.traveledit

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto

interface TravelEditRepository {
    suspend fun editTravelRoom(roomId: Int, travelRoomInfoDto: TravelRoomInfoDto) : NetworkResult<CommonResultDto>

    /**
     * 초대받은 참가자 -> 여행 참가
     */
    suspend fun requestJoinUser(
        roomId: Int,
        createTravelRoomDto: CreateTravelRoomDto
    ): NetworkResult<CommonResultDto>
}