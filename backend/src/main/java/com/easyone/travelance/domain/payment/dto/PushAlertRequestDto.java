package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PushAlertRequestDto {
    private Long paymentId;
    private boolean isWithPaid;
}
