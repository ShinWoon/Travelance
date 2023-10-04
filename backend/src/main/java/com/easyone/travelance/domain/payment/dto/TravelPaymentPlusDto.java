package com.easyone.travelance.domain.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TravelPaymentPlusDto {

    private List<TravelPaymentResponseDto> travelPaymentResponseDto;
    private List<FriendPayment> friendPayments;
    private List<TravelRoomInfo> travelRoomInfo;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendPayment{
        private String nickName;
        private String profileUrl;
        private Long paymentAmount;
        private boolean isDone;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TravelRoomInfo{
        private String startDate;
        private String endDate;
        private Long budget;
        private String travelName;
        private boolean isDone;
    }

}
