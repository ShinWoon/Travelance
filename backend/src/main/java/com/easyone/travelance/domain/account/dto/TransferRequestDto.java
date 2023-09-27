package com.easyone.travelance.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRequestDto {
    private String password;
    @Schema(example = "안적어도됩니당(본인이기에)")
    private String depositNumber;
    private Long amount;
    private String memo;
    private LocalDateTime transferAt;
    private String withdrawalNumber;

    public TransferRequestDto() {
    }

    public TransferRequestDto(String depositNumber, Long amount, String memo
            , LocalDateTime transferAt, String withdrawalNumber){
        this.depositNumber = depositNumber;
        this.amount = amount;
        this.memo = memo;
        this.transferAt = transferAt;
        this.withdrawalNumber = withdrawalNumber;
    }
}
