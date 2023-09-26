package com.easyone.travelance.domain.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomUserRequestDto {
    private String nickName;

    @Builder
    public RoomUserRequestDto(String nickname) {
        this.nickName=nickname;
    }

}
