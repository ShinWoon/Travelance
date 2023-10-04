package com.moneyminions.domain.model.traveldone

import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto

data class TravelDoneInfoTotalDto(
    val allTravelPaymentDto: List<TravelPaymentDto> = listOf(),
    val categoryExpenseDtoList: List<CategoryExpenseDto> = listOf(),
    val myPaymentDtoList: List<TravelPaymentDto> = listOf(),
    val noticeAllDtoList: List<NoticeAllDto> = listOf(),
    val roomUserDtoList: List<RoomUserDto> = listOf(),
    val travelDoneInfoDto: TravelRoomInfoDto = TravelRoomInfoDto(),
)
