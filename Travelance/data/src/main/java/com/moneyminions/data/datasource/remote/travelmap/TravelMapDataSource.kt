package com.moneyminions.data.datasource.remote.travelmap

import com.moneyminions.data.model.travelmap.request.TravelMapDetailRequest
import com.moneyminions.data.model.travelmap.response.TravelMapDetailResponse
import com.moneyminions.data.model.travelmap.response.TravelMapInfoResponse

interface TravelMapDataSource {
    suspend fun getTravelSpotDetail(roomId: Int, travelMapDetailRequest: TravelMapDetailRequest): List<TravelMapDetailResponse>

    suspend fun getTravelMapInfo(roomId: Int): List<TravelMapInfoResponse>
}