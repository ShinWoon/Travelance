package com.easyone.travelance.domain.travel.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomIdResponseDto {
    private Long roomId;

    @Builder
    public RoomIdResponseDto(Long roomId) {
        this.roomId = roomId;
    }


}
