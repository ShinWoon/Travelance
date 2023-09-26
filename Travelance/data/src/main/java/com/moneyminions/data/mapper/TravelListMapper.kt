package com.moneyminions.data.mapper

import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.request.RoomInfoRequest
import com.moneyminions.data.model.travellist.request.RoomUserRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import com.moneyminions.domain.model.travellist.CreateTravelRoomDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.model.travellist.TravelUserDto

fun CreateTravelRoomDto.toData(): CreateTravelRoomRequest {
    return CreateTravelRoomRequest(
        roomUserRequestDto = travelUserInfo.toData(),
        roomInfoRequestDto = travelRoomInfo.toData(),
    )
}

fun TravelUserDto.toData(): RoomUserRequest {
    return RoomUserRequest (
        nickName = nickName,
        profileUrl = profileUrl,
    )
}

fun TravelRoomDto.toData(): RoomInfoRequest {
    return RoomInfoRequest(
        budget = budget,
        endDate = endDate,
        startDate = startDate,
        travelName =travelName,
    )
}

fun TravelRoomResponse.toDomain(): TravelRoomDto{
    return TravelRoomDto(
        budget = budget,
        endDate = endDate,
        isDone = isDone,
        roomId = roomId,
        startDate = startDate,
        travelName = travelName,
        use = use,
    )
}