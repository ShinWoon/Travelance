package com.moneyminions.domain.repository.travellist

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto


interface TravelListRepository {
    suspend fun createTravelRoom(travelRoomDto: TravelRoomDto): NetworkResult<String>

    suspend fun getTravelList(): NetworkResult<List<TravelRoomDto>>
}