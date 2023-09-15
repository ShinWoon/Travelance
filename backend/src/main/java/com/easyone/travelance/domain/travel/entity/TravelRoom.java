package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name="TravelRoom")
public class TravelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long roomNumber;

    private String travelName;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private RoomType isDone=RoomType.BEFORE;

    private Long budget;

    @ManyToOne
    @JoinColumn(name="my_id")
    private Member member; //방 맴버들

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consumption", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    public RoomType getIsDone() {
        return isDone;
    }

    @Builder
    public TravelRoom(String travelName, Long roomNumber, String location, LocalDateTime startDate, LocalDateTime endDate, RoomType isDone, Long budget, Member member) {
        this.travelName= travelName;
        this.roomNumber= roomNumber;
        this.budget= budget;
        this.member=member;
        this.endDate=endDate;
        this.startDate= startDate;
        this.location=location;
        this.isDone=isDone;
    }

}
