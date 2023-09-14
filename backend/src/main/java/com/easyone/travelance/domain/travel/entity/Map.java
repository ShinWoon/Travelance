package com.easyone.travelance.domain.travel.entity;

import javax.persistence.*;

public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //가맹점 주소
    private String address;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private int roomId;
}
