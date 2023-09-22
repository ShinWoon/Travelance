package com.easyone.travelance.domain.travel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_room_id")
    private TravelRoom travelRoom;

    private String title;
    private String content;
    private String link;



}
