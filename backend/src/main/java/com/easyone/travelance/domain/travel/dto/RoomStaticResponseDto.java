package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomStaticResponseDto {
    private Long roomId;
    private Long memberId;
    private String travelName;
    private Long budget;
    private Long percent; // 예산 대비 사용량 %
    private Long UseTotal;
    private Long rest;
    private List<PaymentResponseDto> everyuse;
    private List<PaymentResponseDto> myuse;


    @Builder
    public RoomStaticResponseDto(TravelRoom travelRoom, Member member, Long percent, Long UseTotal, Long rest, List<PaymentResponseDto> everyuse,  List<PaymentResponseDto> myuse) {
        this.travelName = travelRoom.getTravelName();
        this.roomId = travelRoom.getId();
        this.budget = travelRoom.getBudget(); //예산
        this.percent = percent;
        this.UseTotal = UseTotal;
        this.rest= rest;
        this.everyuse=everyuse;
        this.myuse=myuse;
        this.memberId=member.getId(); //내 정보
    }

}
