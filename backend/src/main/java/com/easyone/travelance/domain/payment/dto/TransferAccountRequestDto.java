package com.easyone.travelance.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransferAccountRequestDto {
    private Long roomNumber;
    private String password;
}
