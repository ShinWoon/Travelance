package com.moneyminions.data.mapper

import com.moneyminions.data.model.traveldone.response.AllTravelPaymentResponseDto
import com.moneyminions.data.model.traveldone.response.CategoryExpenseResponseDto
import com.moneyminions.data.model.traveldone.response.MyPaymentResponseDto
import com.moneyminions.data.model.traveldone.response.NoticeAllResponseDto
import com.moneyminions.data.model.traveldone.response.RoomUserResponseDto
import com.moneyminions.data.model.traveldone.response.TravelDoneInfoResponseDto
import com.moneyminions.data.model.traveldone.response.TravelDoneTotalInfoResponse
import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.model.traveldone.CategoryExpenseDto
import com.moneyminions.domain.model.traveldone.NoticeAllDto
import com.moneyminions.domain.model.traveldone.RoomUserDto
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto

fun TravelDoneTotalInfoResponse.toDomain(): TravelDoneInfoTotalDto{
    return TravelDoneInfoTotalDto(
        allTravelPaymentDto = allTravelPaymentResponseDto.map { it.toDomain() },
        categoryExpenseDtoList = categoryExpenseDtoList.map { it.toDomain() },
        myPaymentDtoList = myPaymentResponseDtoList.map { it.toDomain() },
        noticeAllDtoList = noticeAllResponseDtoList.map { it.toDomain() },
        roomUserDtoList = roomUserResponseDtoList.map { it.toDomain() },
        travelDoneInfoDto = travelDoneInfoResponseDto.toDomain(),
    )
}

fun AllTravelPaymentResponseDto.toDomain(): TravelPaymentDto{
    return TravelPaymentDto(
        isWithPaid = isWithPaid,
        paymentContent = paymentContent,
        paymentAmount = paymentAmount,
        paymentAt = paymentAt,
        paymentId = paymentId,
        storeAddress = storeAddress,
        storeSector = storeSector,
    )
}

fun CategoryExpenseResponseDto.toDomain(): CategoryExpenseDto{
    return CategoryExpenseDto(
        category = category,
        percent = percent,
    )
}

fun MyPaymentResponseDto.toDomain(): TravelPaymentDto{
    return TravelPaymentDto(
        isWithPaid = isWithPaid,
        paymentContent = paymentContent,
        paymentAmount = paymentAmount,
        paymentAt = paymentAt,
        paymentId = paymentId,
        storeAddress = storeAddress,
        storeSector = storeSector,
    )
}

fun NoticeAllResponseDto.toDomain(): NoticeAllDto{
    return NoticeAllDto(
        noticeId = noticeId,
        title = title,
        content = content,
        link = link,
    )
}

fun RoomUserResponseDto.toDomain(): RoomUserDto{
    return RoomUserDto(
        email = email,
        travelNickname = travelNickname,
        profileUrl = profileUrl,
    )
}

fun TravelDoneInfoResponseDto.toDomain(): TravelRoomInfoDto{
    return TravelRoomInfoDto(
        travelName = travelName,
        startDate = startDate,
        endDate = endDate,
        budget = use,
    )
}