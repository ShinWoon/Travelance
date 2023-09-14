package com.easyone.travelance.domain.travel.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumptionId;

//    @ManyToOne
//    @JoinColumn(name="member_id")
//    private Member member; //방 맴버들

    @ManyToOne
    @JoinColumn(name = "room_id")
    private int travelId;

    private LocalDateTime tranDate;

    private int price;

    private String content;

    private String category;//업종 나중에 분류하려면 ENUM타입으로 RESPOSNSE?
    private String address;




}
