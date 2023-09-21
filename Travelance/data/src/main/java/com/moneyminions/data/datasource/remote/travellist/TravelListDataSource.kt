package com.moneyminions.data.datasource.remote.travellist

import com.moneyminions.domain.model.travellist.request.CreateTravelRoomRequest

interface TravelListDataSource {
    suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest)
    
}