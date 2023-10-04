package com.moneyminions.domain.usecase.traveledit

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.repository.traveledit.TravelEditRepository
import javax.inject.Inject

class RequestJoinUserUseCase @Inject constructor(
    private val travelEditRepository: TravelEditRepository
){
    suspend operator fun invoke(roomId: Int, createTravelRoomDto: CreateTravelRoomDto): NetworkResult<CommonResultDto> {
        return travelEditRepository.requestJoinUser(
            roomId = roomId,
            createTravelRoomDto = createTravelRoomDto,
        )
    }
}