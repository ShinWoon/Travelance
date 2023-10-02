package com.moneyminions.domain.usecase.traveledit

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.repository.traveledit.TravelEditRepository
import javax.inject.Inject

class EditTravelRoomInfoUseCase @Inject constructor(
    private val travelEditRepository: TravelEditRepository
) {
    suspend operator fun invoke(roomId: Int, travelRoomInfoDto: TravelRoomInfoDto) : NetworkResult<CommonResultDto> {
        return travelEditRepository.editTravelRoom(roomId = roomId, travelRoomInfoDto = travelRoomInfoDto)
    }
}