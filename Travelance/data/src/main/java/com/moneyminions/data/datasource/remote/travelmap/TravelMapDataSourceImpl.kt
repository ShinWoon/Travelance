package com.moneyminions.data.datasource.remote.travelmap

import com.moneyminions.data.model.travelmap.request.TravelMapDetailRequest
import com.moneyminions.data.model.travelmap.response.TravelMapDetailResponse
import com.moneyminions.data.model.travelmap.response.TravelMapInfoResponse
import com.moneyminions.data.service.BusinessService

class TravelMapDataSourceImpl(
    private val businessService: BusinessService
): TravelMapDataSource {
    override suspend fun getTravelSpotDetail(
        roomId: Int,
        travelMapDetailRequest: TravelMapDetailRequest
    ): List<TravelMapDetailResponse> {
        return businessService.getTravelSpotDetail(roomId = roomId, travelMapDetailRequest = travelMapDetailRequest)
    }

    override suspend fun getTravelMapInfo(roomId: Int): List<TravelMapInfoResponse> {
        return businessService.getTravelSpots(roomId = roomId)
    }
}