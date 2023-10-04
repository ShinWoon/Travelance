package com.easyone.travelance.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransferInfoDto {
    private TravelRoomInfo travelRoomInfo;
    private PaymentInfo paymentInfo;
    private List<ReceiveInfo> receiveInfos;
    private List<SendInfo> sendInfos;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TravelRoomInfo{
        private String startDate;
        private String endDate;
        private Long budget;
        private String travelName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo{
        private Long totalAmount;
        private Long perAmount;
        private Long myAmount;
        private Long transferTotalAmount;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReceiveInfo{
        private String nickName;
        private String profileUrl;
        private Long paymentAmount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendInfo{
        private String nickName;
        private String profileUrl;
        private Long paymentAmount;
    }
}
