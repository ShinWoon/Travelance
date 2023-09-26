package com.easyone.travelance.domain.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomUserRequestDto {
    private String nickname;

    @Builder
    public RoomUserRequestDto(String nickname) {
        this.nickname=nickname;
    }

}
