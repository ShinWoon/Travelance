package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomStaticResponseDto {
    private Long roomId;
    private String travelName;
    private Long budget;
    private double percent; // 예산 대비 사용량 %
    @JsonProperty("useTotal")
    private Long UseTotal;
    private Long rest;
    private String isDone;
    private List<PaymentResponseDto> everyuse;
    private List<PaymentResponseDto> myuse;


    @Builder
    public RoomStaticResponseDto(TravelRoom travelRoom, double percent, Long UseTotal, Long rest, List<PaymentResponseDto> everyuse,  List<PaymentResponseDto> myuse) {
        this.travelName = travelRoom.getTravelName();
        this.roomId = travelRoom.getId();
        this.budget = travelRoom.getBudget(); //예산
        this.isDone = travelRoom.getIsDone().toString();
        this.percent = percent;
        this.UseTotal = UseTotal;
        this.rest= rest;
        this.everyuse=everyuse;
        this.myuse=myuse;
    }

}
