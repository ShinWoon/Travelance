package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.home.TravelRoomInfoDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class GetTravelRoomInfoUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(roomId: Int) :NetworkResult<TravelRoomInfoDto>{
        return homeRepository.getTravelRoomInfo(roomId = roomId)
    }
}