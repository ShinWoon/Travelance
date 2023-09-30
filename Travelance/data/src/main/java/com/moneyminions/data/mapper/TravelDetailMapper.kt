package com.moneyminions.data.mapper

import com.moneyminions.data.model.traveldetail.response.FriendPaymentResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.model.traveldetail.response.TravelPaymentResponse
import com.moneyminions.data.model.traveldetail.response.TravelRoomInfoResponse
import com.moneyminions.domain.model.traveldetail.FriendPaymentDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto

fun TravelDetailMyPaymentResponse.toDomain(): TravelPaymentDto {
    return TravelPaymentDto(
        isWithPaid = isWithPaid,
        paymentContent = paymentContent,
        paymentAmount = paymentAmount,
        paymentAt = paymentAt,
        paymentId = paymentId,
    )
}

fun TravelDetailInfoResponse.toDomain(): TravelDetailInfoDto {
    return TravelDetailInfoDto(
        friendPayments = friendPayments.map { it.toDomain() },
        travelPayment = travelPaymentResponseDto.map { it.toDomain() },
        travelRoomInfo = travelRoomInfo.map { it.toDomain() },
    )
}

fun FriendPaymentResponse.toDomain(): FriendPaymentDto {
    return FriendPaymentDto(
        done = done,
        nickName = nickName,
        paymentAmount = paymentAmount,
        profileUrl = profileUrl,
    )
}

fun TravelPaymentResponse.toDomain(): TravelPaymentDto {
    return TravelPaymentDto(
        isWithPaid = isWithPaid,
        paymentContent = paymentContent,
        paymentAmount = paymentAmount,
        paymentAt = paymentAt,
        paymentId = paymentId,
    )
}

fun TravelRoomInfoResponse.toDomain(): TravelRoomInfoDto {
    return TravelRoomInfoDto(
        budget = budget,
        endDate = endDate,
        startDate = startDate,
    )
}
