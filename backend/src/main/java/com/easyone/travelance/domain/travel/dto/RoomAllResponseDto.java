package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.travel.entity.Consumption;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class RoomAllResponseDto {
    private int roomId;
    private String travelName;
    private String location;
    private String startDate;
    private String endDate;

    private RoomType isDone;
    private int budget;
    //현재 사용한 금액(총액)
    private int use;


    public RoomAllResponseDto(TravelRoom entity, int totalprice) {
        this.roomId=entity.getRoomNumber();
        this.travelName=entity.getTravelName();
        this.budget=entity.getBudget();
        this.endDate=entity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.startDate=entity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.location=entity.getLocation();
        this.isDone=entity.getIsDone();
        this.use=totalprice;
    }


}
