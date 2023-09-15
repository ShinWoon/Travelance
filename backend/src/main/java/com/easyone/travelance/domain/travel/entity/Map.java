package com.easyone.travelance.domain.travel.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가맹점 주소
    private String address;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Long roomId;
}
