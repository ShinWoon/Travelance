package com.moneyminions.domain.usecase.home

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.home.HomeRepository
import javax.inject.Inject

class StartTravelUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(roomId: Int) : NetworkResult<CommonResultDto> {
        return homeRepository.startTravel(roomId = roomId)
    }
}