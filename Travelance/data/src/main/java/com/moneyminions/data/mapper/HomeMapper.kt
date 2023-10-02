package com.moneyminions.data.mapper

import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.TravelRoomFriendsResponse
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse
import com.moneyminions.data.model.home.response.TravelRoomMemberUseResponse
import com.moneyminions.domain.model.home.CashDto
import com.moneyminions.domain.model.home.TravelMemberUseDto
import com.moneyminions.domain.model.home.TravelRoomFriendDto
import com.moneyminions.domain.model.home.TravelRoomInfoDto

fun TravelRoomInfoResponse.toDomain(): TravelRoomInfoDto {
    return TravelRoomInfoDto (
        roomId =roomId,
        travelName = travelName,
        budget = budget,
        percent = percent,
        rest = rest,
        isDone = isDone,
        everyuse = everyuse.map { it.toDomain() }.toMutableList(),
        myuse = myuse.map { it.toDomain() }.toMutableList(),
        useTotal = useTotal,
    )
}

fun TravelRoomMemberUseResponse.toDomain(): TravelMemberUseDto {
    return TravelMemberUseDto(
        paymentId = paymentId,
        price = price,
        content = content,
        category = category,
        address = address,
        memberId = memberId,
        nickName = nickName,
        paymentAt = paymentAt,
    )
}

fun CashDto.toData(): UseCashRequest {
    return UseCashRequest(
        roomNumber = roomNumber,
        paymentContent = paymentContent,
        paymentAmount = paymentAmount,
    )
}

fun TravelRoomFriendsResponse.toDomain(): TravelRoomFriendDto {
    return TravelRoomFriendDto(
        email = email,
        profileUrl = profileUrl,
        travelNickname = travelNickname,
    )
}

