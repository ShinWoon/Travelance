package com.moneyminions.domain.repository.travelmap

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto

interface TravelMapRepository {

    suspend fun getTravelSpots(roomId: Int): NetworkResult<List<TravelMapSpotDto>>

    suspend fun getTravelSpotDetail(roomId: Int, travelMapStoreAddressDto: TravelMapStoreAddressDto): NetworkResult<List<TravelMapDetailDto>>
}