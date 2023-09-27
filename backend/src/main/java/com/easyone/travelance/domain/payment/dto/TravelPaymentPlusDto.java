package com.easyone.travelance.domain.payment.dto;


import lombok.Data;

import java.util.List;

@Data
public class TravelPaymentPlusDto {

    private List<TravelPaymentResponseDto> travelPaymentResponseDto;
    private List<FriendPayment> friendPayments;
    private List<TravelRoomInfo> travelRoomInfo;

    public static class FriendPayment{
        private String nickName;
        private String profileUrl;
        private Long paymentAmount;
        private boolean isDone;
    }

    public static class TravelRoomInfo{
        private String startDate;
        private String endDate;
        private Long budget;
    }

}
