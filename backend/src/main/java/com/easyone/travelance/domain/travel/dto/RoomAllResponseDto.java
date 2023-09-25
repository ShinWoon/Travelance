package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class RoomAllResponseDto {
    private Long roomId;
    private String travelName;
    private String startDate;
    private String endDate;
    private RoomType isDone;
    private Long budget;
    //현재 사용한 금액(총액)
    private Long use;


    public RoomAllResponseDto(TravelRoom entity, Long totalprice) {
        this.roomId=entity.getId();
        this.travelName=entity.getTravelName();
        this.budget=entity.getBudget();
        this.endDate=entity.getEndDate();
        this.startDate=entity.getStartDate();
        this.isDone=entity.getIsDone();
        this.use=totalprice;
    }


}
