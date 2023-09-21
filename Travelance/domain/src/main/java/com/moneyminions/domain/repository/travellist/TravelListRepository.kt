package com.moneyminions.domain.repository.travellist

import com.moneyminions.domain.model.travellist.request.CreateTravelRoomRequest

interface TravelListRepository {
    suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest)
}