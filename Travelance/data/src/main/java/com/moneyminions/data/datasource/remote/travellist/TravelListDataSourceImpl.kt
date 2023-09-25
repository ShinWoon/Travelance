package com.moneyminions.data.datasource.remote.travellist

import android.util.Log
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.data.service.BusinessService


private const val TAG = "TravelListDataSourceImpl_D210"
class TravelListDataSourceImpl(
    private val businessService: BusinessService
): TravelListDataSource {

    /**
     * 여행 방 생성
     */
    override suspend fun createTravelRoom(createTravelRoomRequest: CreateTravelRoomRequest): String {
        Log.d(TAG, "createTravelRoom: $createTravelRoomRequest")
        return businessService.createTravelRoom(createTravelRoomRequest)
    }

    /**
     * 여행 목록
     */
    override suspend fun getTravelList(): List<TravelRoomResponse> {
        return businessService.travelList()
    }
}