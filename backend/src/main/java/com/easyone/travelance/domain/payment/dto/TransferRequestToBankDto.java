package com.easyone.travelance.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestToBankDto {
    private String depositNumber;
    private Long amount;
    private String memo;
    private String withdrawalNumber;
}
