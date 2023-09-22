package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class RoomInfoRequestDto {
    private String travelName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long budget;

    @Builder
    public RoomInfoRequestDto(String travelName, LocalDateTime startDate, LocalDateTime endDate, Long budget) {
        this.travelName = travelName;
        this.startDate=startDate;
        this.endDate= endDate;
        this.budget = budget;
    }

    public TravelRoom toEntity(RoomType isDone) {
        return TravelRoom.builder()
                .budget(budget)
                .endDate(endDate)
                .isDone(isDone)
                .startDate(startDate)
                .endDate(endDate)
                .travelName(travelName)
                .build();
    }

}
