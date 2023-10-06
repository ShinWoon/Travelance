package com.moneyminions.data.datasource.remote.traveldone

import com.moneyminions.data.model.traveldone.response.TravelDoneTotalInfoResponse
import com.moneyminions.data.service.BusinessService

class TravelDoneDataSourceImpl(
    private val businessService: BusinessService
): TravelDoneDataSource {
    override suspend fun getTravelDoneInfo(roomId: Int): TravelDoneTotalInfoResponse {
        return businessService.getTravelDoneInfo(roomId = roomId)
    }
}