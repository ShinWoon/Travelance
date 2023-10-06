package com.moneyminions.data.model.traveldone.response


import com.google.gson.annotations.SerializedName

data class TravelDoneTotalInfoResponse(
    @SerializedName("allTravelPaymentResponseDto")
    val allTravelPaymentResponseDto: List<AllTravelPaymentResponseDto>,
    @SerializedName("categoryExpenseDtoList")
    val categoryExpenseDtoList: List<CategoryExpenseResponseDto>,
    @SerializedName("myPaymentResponseDtoList")
    val myPaymentResponseDtoList: List<MyPaymentResponseDto>,
    @SerializedName("noticeAllResponseDtoList")
    val noticeAllResponseDtoList: List<NoticeAllResponseDto>,
    @SerializedName("roomUserResponseDtoList")
    val roomUserResponseDtoList: List<RoomUserResponseDto>,
    @SerializedName("travelDoneInfoResponseDto")
    val travelDoneInfoResponseDto: TravelDoneInfoResponseDto
)