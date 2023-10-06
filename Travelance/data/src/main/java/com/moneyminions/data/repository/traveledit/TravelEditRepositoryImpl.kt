package com.moneyminions.data.repository.traveledit

import com.moneyminions.data.datasource.remote.traveledit.TravelEditDataSource
import com.moneyminions.data.mapper.toData
import com.moneyminions.data.mapper.toDomain
import com.moneyminions.data.service.handleApi
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.repository.traveledit.TravelEditRepository

class TravelEditRepositoryImpl(
    private val travelEditDataSource: TravelEditDataSource
) : TravelEditRepository {
    override suspend fun editTravelRoom(
        roomId: Int,
        travelRoomInfoDto: TravelRoomInfoDto
    ): NetworkResult<CommonResultDto> {
        return handleApi {
            travelEditDataSource.editTravelRoom(
                roomId = roomId,
                travelRoomEditRequest = travelRoomInfoDto.toData()
            ).toDomain()
        }
    }

    /**
     * 초대받은 참가자 -> 여행 참가
     */
    override suspend fun requestJoinUser(
        roomId: Int,
        createTravelRoomDto: CreateTravelRoomDto
    ): NetworkResult<CommonResultDto> {
        return handleApi {
            travelEditDataSource.requestJoinUser(
                roomId = roomId,
                createTravelRoomRequest = createTravelRoomDto.toData(),
            ).toDomain()
        }
    }

}