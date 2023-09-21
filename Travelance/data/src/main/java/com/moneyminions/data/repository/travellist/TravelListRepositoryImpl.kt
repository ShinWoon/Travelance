package com.moneyminions.data.repository.travellist

import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.domain.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.domain.repository.travellist.TravelListRepository
import javax.inject.Inject

class TravelListRepositoryImpl @Inject constructor(
    val travelListDataSource: TravelListDataSource
): TravelListRepository{
    override suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest) {
        travelListDataSource.createTravelRoom(createTravelRoomRequest)
    }
    
}