package com.moneyminions.data.datasource.remote.travellist

import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.request.TravelListRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.data.service.BusinessService


class TravelListDataSourceImpl(
    private val businessService: BusinessService
): TravelListDataSource {

    /**
     * 여행 방
     */
    override suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest) {
        businessService.createTravelRoom(createTravelRoomRequest)
    }

    /**
     * 여행 목록
     */
    override suspend fun getTravelList(): List<TravelRoomResponse> {
        return businessService.travelList()
    }
}