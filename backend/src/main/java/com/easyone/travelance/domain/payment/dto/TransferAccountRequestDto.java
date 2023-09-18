package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferAccountRequestDto {
    private Long roomNumber;
    private Long fromMemberId;
    private Long toMemberId;
    private Long amount;
}
