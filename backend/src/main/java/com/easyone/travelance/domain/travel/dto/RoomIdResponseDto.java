package com.easyone.travelance.domain.travel.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomIdResponseDto {
    private String result;

    @Builder
    public RoomIdResponseDto(String roomId) {
        this.result = roomId;
    }


}
