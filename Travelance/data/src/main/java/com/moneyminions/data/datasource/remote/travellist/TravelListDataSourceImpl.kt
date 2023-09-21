package com.moneyminions.data.datasource.remote.travellist

import com.moneyminions.data.service.BusinessService
import com.moneyminions.domain.model.travellist.request.CreateTravelRoomRequest

class TravelListDataSourceImpl(
    private val businessService: BusinessService
): TravelListDataSource {
    override suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest) {
        businessService.createTravelRoom(createTravelRoomRequest)
    }
}