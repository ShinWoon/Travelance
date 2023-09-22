package com.moneyminions.data.datasource.remote.travellist

import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse


interface TravelListDataSource {
    suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest): String

    suspend fun getTravelList(): List<TravelRoomResponse>
}