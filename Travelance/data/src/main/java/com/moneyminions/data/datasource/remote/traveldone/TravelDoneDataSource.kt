package com.moneyminions.data.datasource.remote.traveldone

import com.moneyminions.data.model.traveldone.response.TravelDoneTotalInfoResponse

interface TravelDoneDataSource {
    suspend fun getTravelDoneInfo(roomId: Int): TravelDoneTotalInfoResponse
}