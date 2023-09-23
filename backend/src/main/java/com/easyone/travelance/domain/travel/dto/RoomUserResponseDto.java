package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomUserResponseDto {
    private String email;
    private String nickName;


    @Builder
    public RoomUserResponseDto(Member member) {
        this.email=member.getEmail();
        this.nickName=member.getNickname();

    }


}
