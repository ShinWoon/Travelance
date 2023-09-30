package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapAllResponseDto {
    private String storeAddress;
    private String storeSector;

    public MapAllResponseDto(Payment payment) {
        storeAddress=payment.getStoreAddress();
        storeSector= payment.getStoreSector();
    }
}
