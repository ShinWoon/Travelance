package com.moneyminions.data.mapper

import com.moneyminions.data.model.traveledit.request.TravelRoomEditRequest
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto

fun TravelRoomInfoDto.toData(): TravelRoomEditRequest {
    return TravelRoomEditRequest(
        budget = budget,
        endDate = endDate,
        startDate = startDate,
        travelName = travelName
    )
}