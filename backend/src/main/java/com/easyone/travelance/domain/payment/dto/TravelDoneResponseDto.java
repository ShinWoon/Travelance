package com.easyone.travelance.domain.payment.dto;

import com.easyone.travelance.domain.travel.dto.NoticeAllResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomUserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TravelDoneResponseDto {

    private TravelDoneInfoResponseDto travelDoneInfoResponseDto;
    private List<NoticeAllResponseDto> noticeAllResponseDtoList;
    private List<TravelPaymentResponseDto> AllTravelPaymentResponseDto;
    private List<CategoryExpenseDto> categoryExpenseDtoList;
    private List<TravelPaymentResponseDto> MyPaymentResponseDtoList;
    private List<RoomUserResponseDto> roomUserResponseDtoList;

    public TravelDoneResponseDto(TravelDoneInfoResponseDto travelDoneInfoResponseDto, List<NoticeAllResponseDto> noticeAllResponseDtoList, List<TravelPaymentResponseDto> AllTravelPaymentResponseDto,
                                     List<CategoryExpenseDto> categoryExpenseDtoList, List<TravelPaymentResponseDto> MyPaymentResponseDtoList,
                                     List<RoomUserResponseDto> roomUserResponseDtoList) {
        this.travelDoneInfoResponseDto=travelDoneInfoResponseDto;
        this.noticeAllResponseDtoList = noticeAllResponseDtoList;
        this.AllTravelPaymentResponseDto = AllTravelPaymentResponseDto;
        this.categoryExpenseDtoList=categoryExpenseDtoList;
        this.MyPaymentResponseDtoList = MyPaymentResponseDtoList;
        this.roomUserResponseDtoList = roomUserResponseDtoList;

    }



}
