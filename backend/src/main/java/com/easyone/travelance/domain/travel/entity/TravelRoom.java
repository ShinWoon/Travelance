package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.travel.enumclass.roomType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="TravelRoom")
public class TravelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int roomNumber;

    private String travelName;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private roomType isDone;

    private int budget;


//    @ManyToOne
//    @JoinColumn(name="my_id")
//    private Member member; //방 맴버들




    @Builder
    public TravelRoom() {

    }

}
