package com.easyone.travelance.domain.payment.dto;


import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TravelDoneInfoResponseDto {
    private String travelName;
    private String startDate;
    private String endDate;
    //현재 사용한 금액(총액)
    private Long use;


    public TravelDoneInfoResponseDto(TravelRoom entity, Long totalprice) {
        this.travelName = entity.getTravelName();
        this.endDate = entity.getEndDate();
        this.startDate = entity.getStartDate();
        this.use = totalprice;
    }
}
