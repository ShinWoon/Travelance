package com.moneyminions.data.mapper

import com.moneyminions.data.model.traveldetail.request.FinalPaymentRequest
import com.moneyminions.data.model.traveldetail.request.PaymentCompleteRequest
import com.moneyminions.data.model.traveldetail.request.PaymentWithRequest
import com.moneyminions.data.model.traveldetail.request.TravelPaymentChangeInfoRequest
import com.moneyminions.data.model.traveldetail.response.FriendPaymentResponse
import com.moneyminions.data.model.traveldetail.response.SettleResultPaymentInfoResponse
import com.moneyminions.data.model.traveldetail.response.SettleResultResponse
import com.moneyminions.data.model.traveldetail.response.SettleResultRoomInfoResponse
import com.moneyminions.data.model.traveldetail.response.SettleResultUserInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailInfoResponse
import com.moneyminions.data.model.traveldetail.response.TravelDetailMyPaymentResponse
import com.moneyminions.data.model.traveldetail.response.TravelPaymentResponse
import com.moneyminions.data.model.traveldetail.response.TravelRoomInfoResponse
import com.moneyminions.domain.model.traveldetail.FinalPaymentDto
import com.moneyminions.domain.model.traveldetail.FriendPaymentDto
import com.moneyminions.domain.model.traveldetail.PaymentCompleteDto
import com.moneyminions.domain.model.traveldetail.SettleResultDto
import com.moneyminions.domain.model.traveldetail.SettleResultPaymentInfoDto
import com.moneyminions.domain.model.traveldetail.SettleResultRoomInfoDto
import com.moneyminions.domain.model.traveldetail.SettleResultUserInfoDto
import com.moneyminions.domain.model.traveldetail.TravelDetailInfoDto
import com.moneyminions.domain.model.traveldetail.TravelPaymentChangeInfoDto
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
        travelName = travelName,
        done = done,
    )
}

fun TravelPaymentChangeInfoDto.toData(): TravelPaymentChangeInfoRequest {
    return TravelPaymentChangeInfoRequest(
        paymentId = paymentId,
        withPaid = withPaid,
    )
}

fun PaymentCompleteDto.toData(): PaymentCompleteRequest {
    return PaymentCompleteRequest(
        paymentWithList = paymentWithList.map { it.toData() },
        roomNumber = roomNumber,
    )
}

fun TravelPaymentDto.toData(): PaymentWithRequest {
    return PaymentWithRequest(
        isWithPaid = isWithPaid,
        paymentAmount = paymentAmount,
        paymentAt = paymentAt,
        paymentContent = paymentContent,
        paymentId = paymentId,
        storeAddress = storeAddress,
        storeSector = storeSector,
    )
}

fun SettleResultPaymentInfoResponse.toDomain(): SettleResultPaymentInfoDto{
    return SettleResultPaymentInfoDto(
        myAmount = myAmount,
        perAmount = perAmount,
        totalAmount = totalAmount,
        transferTotalAmount = transferTotalAmount
    )
}

fun SettleResultRoomInfoResponse.toDomain(): SettleResultRoomInfoDto{
    return SettleResultRoomInfoDto(
        budget = budget,
        endDate = endDate,
        startDate = startDate,
        travelName = travelName
    )
}

fun SettleResultUserInfoResponse.toDomain(): SettleResultUserInfoDto{
    return SettleResultUserInfoDto(
        nickName = nickName,
        paymentAmount = paymentAmount,
        profileUrl = profileUrl
    )
}

fun SettleResultResponse.toDomain(): SettleResultDto{
    return SettleResultDto(
        paymentInfo = paymentInfo.toDomain(),
        travelRoomInfo = travelRoomInfo.toDomain(),
        receiveInfos = receiveInfos.map { it.toDomain() },
        sendInfos = sendInfos.map { it.toDomain() }
    )
}

fun FinalPaymentDto.toData(): FinalPaymentRequest{
    return FinalPaymentRequest(
        password = password,
        roomNumber = roomNumber
    )
}