package com.moneyminions.domain.usecase.travellist

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject

class GetTravelListUseCase @Inject constructor(
    private val travelListRepository: TravelListRepository
){
    suspend operator fun invoke(): NetworkResult<List<TravelRoomDto>>{
        return travelListRepository.getTravelList()
    }
}