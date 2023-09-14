package com.easyone.travelance.domain.travel.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private TravelRoom travelRoom;

    private String title;
    private String content;
    private String link;



}
