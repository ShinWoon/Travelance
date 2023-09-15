package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.Consumption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@NoArgsConstructor
public class ConsumptionResponseDto {
    private Long consumptionId;
    private Long price;
    private String content;
    private String category;
    private String address;
    private Long memeberId;
    private String nickname;

    public ConsumptionResponseDto(Consumption consumption, Member member) {
        this.consumptionId = consumption.getConsumptionId();
        this.price = consumption.getPrice();
        this.address=consumption.getAddress();
        this.category = consumption.getCategory();
        this.content= consumption.getContent();
        this.memeberId = member.getId();
        this.nickname = member.getNickname();
    }
}
