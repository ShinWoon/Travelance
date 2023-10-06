package com.moneyminions.domain.usecase.travellist

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject

class DeleteTravelRoomUseCase @Inject constructor(
    private val travelListRepository: TravelListRepository
){
    suspend operator fun invoke(roomId: Int) : NetworkResult<CommonResultDto> {
        return travelListRepository.deleteTravelRoom(roomId = roomId)
    }
}