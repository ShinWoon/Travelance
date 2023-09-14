package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompleteCalculationRequestDto {
    private Long roomNumber;
    private Long memberId;
}
