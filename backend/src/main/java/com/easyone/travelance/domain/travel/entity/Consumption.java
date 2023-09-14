package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.MemberAuth;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumptionId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberAuth member; // 소비한 주체

    @ManyToOne
    @JoinColumn(name = "room_id")
    private int travelId;

    private LocalDateTime tranDate;

    private int price;

    private String content;

    private String category;//업종 나중에 분류하려면 ENUM타입으로 RESPOSNSE?
    private String address;

    @Builder
    public Consumption(LocalDateTime transDate, int price, String content, String category, String address) {
        this.tranDate = transDate;
        this.price=price;
        this.content=content;
        this.category=category;
        this.address=address;
    }



}
