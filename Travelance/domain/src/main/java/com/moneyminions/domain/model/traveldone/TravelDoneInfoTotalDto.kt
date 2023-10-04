package com.moneyminions.domain.model.traveldone

import com.moneyminions.domain.model.traveldetail.TravelPaymentDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto

data class TravelDoneInfoTotalDto(
    val allTravelPaymentDto: List<TravelPaymentDto>,
    val categoryExpenseDtoList: List<CategoryExpenseDto>,
    val myPaymentDtoList: List<TravelPaymentDto>,
    val noticeAllDtoList: List<NoticeAllDto>,
    val roomUserDtoList: List<RoomUserDto>,
    val travelDoneInfoDto: TravelRoomInfoDto
)