package com.easyone.travelance.domain.member.dto;

import lombok.Data;

@Data
public class MyCardDto {
    private String cardNumber;
    private String cardCoName;
    private Long idx;

    // 생성자, getter, setter 등 필요한 메소드 추가
}
