package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.Consumption;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomStaticResponseDto {
    private Long roomId;
    private Long memberId;
    private String travelName;
    private Long budget;
    private Long percent;


    @Builder
    public RoomStaticResponseDto(TravelRoom travelRoom, Member member, Long percent) {
        this.travelName = travelRoom.getTravelName();
        this.roomId = travelRoom.getRoomNumber();
        this.budget = travelRoom.getBudget(); //예산
        this.percent = percent;


    }

}
