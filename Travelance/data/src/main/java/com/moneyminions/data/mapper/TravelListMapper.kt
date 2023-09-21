package com.moneyminions.data.mapper

import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.domain.model.travellist.TravelRoomDto

fun TravelRoomDto.toData(): CreateTravelRoomRequest{
    return CreateTravelRoomRequest(
        budget = budget,
        endDate = endDate,
        location = location,
        startDate = startDate,
        travelName = travelName
    )
}

fun TravelRoomResponse.toDomain(): TravelRoomDto{
    return TravelRoomDto(
        budget = budget,
        endDate = endDate,
        isDone = isDone,
        location = location,
        roomId = roomId,
        startDate = startDate,
        travelName = travelName,
        use = use,
    )
}