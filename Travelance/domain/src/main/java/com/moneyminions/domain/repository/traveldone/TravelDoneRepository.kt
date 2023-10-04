package com.moneyminions.domain.repository.traveldone

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto

interface TravelDoneRepository {
    suspend fun getTravelDoneInfo(roomId: Int): NetworkResult<TravelDoneInfoTotalDto>
}