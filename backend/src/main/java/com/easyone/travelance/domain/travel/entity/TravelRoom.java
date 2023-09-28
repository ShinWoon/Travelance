package com.easyone.travelance.domain.travel.entity;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    private String travelName;
    private String startDate;
    private String endDate;

    @Enumerated(EnumType.STRING)
    private RoomType isDone=RoomType.BEFORE;

    private Long budget;

    @OneToMany(mappedBy = "travelRoom", cascade = CascadeType.ALL,  orphanRemoval = true)
    @JsonManagedReference
    private List<TravelRoomMember> travelRoomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "travelRoom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    public RoomType getIsDone() {
        return isDone;
    }

    @Builder
    public TravelRoom(String travelName, Long id, String startDate, String endDate, RoomType isDone, Long budget) {
        this.travelName= travelName;
        this.id= id;
        this.budget= budget;
        this.endDate=endDate;
        this.startDate= startDate;
        this.isDone=isDone;
    }

    //여행방 수정
    public void update(RoomInfoRequestDto roomInfoRequestDto) {
        this.travelName= roomInfoRequestDto.getTravelName();
        this.startDate=roomInfoRequestDto.getStartDate();
        this.endDate=roomInfoRequestDto.getEndDate();
        this.budget= roomInfoRequestDto.getBudget();
    }

    public void setRoomType(RoomType roomType){
        this.isDone = roomType;
    }
}
